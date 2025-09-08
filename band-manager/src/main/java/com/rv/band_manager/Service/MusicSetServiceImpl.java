package com.rv.band_manager.Service;

import org.springframework.stereotype.*;

import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Model.MusicPart;
import com.rv.band_manager.Model.MusicSet;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Repository.BandRepository;
import com.rv.band_manager.Repository.MusicSetRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service implementation for managing MusicSets and their associations with Bands.
 */
@Service
public class MusicSetServiceImpl implements MusicSetService{
    private final MusicSetRepository musicSetRepository;
    private final BandRepository bandRepository;

    /**
     * Constructor for MusicSetServiceImpl.
     *
     * @param musicSetRepository The repository used to interact with the MusicSet data.
     * @param bandRepository The repository used to interact with the Band data.
     */
    public MusicSetServiceImpl(MusicSetRepository musicSetRepository,
                               BandRepository bandRepository) {
        this.musicSetRepository = musicSetRepository;
        this.bandRepository = bandRepository;
    }

    /**
     * Retrieves all MusicSets from the repository.
     *
     * @return A list of all MusicSets.
     */
    public List<MusicSet> getAllMusicSets() {
        return musicSetRepository.findAll();
    }

    /**
     * Retrieves a MusicSet by its ID.
     *
     * @param id The ID of the MusicSet to retrieve.
     * @return An Optional containing the MusicSet if found, or an empty Optional if not found.
     */
    public Optional<MusicSet> getMusicSetById(Long id) {
        return musicSetRepository.findById(id);
    }

    /**
     * Saves a new MusicSet or updates an existing MusicSet.
     *
     * @param musicSet The MusicSet to save or update.
     * @return The saved MusicSet.
     */
    public MusicSet saveMusicSet(MusicSet musicSet) {
        return musicSetRepository.save(musicSet);
    }

    /**
     * Updates an existing MusicSet by its ID.
     *
     * @param id The ID of the MusicSet to update.
     * @param updatedMusicSet The updated MusicSet with new data.
     * @return The updated MusicSet.
     * @throws RuntimeException If the MusicSet with the specified ID is not found.
     */
    public MusicSet updateMusicSet(Long id, MusicSet updatedMusicSet) {
        Optional<MusicSet> musicSetOptional = musicSetRepository.findById(id);
        if (musicSetOptional.isPresent()) {
            MusicSet musicSet = musicSetOptional.get();
            musicSet.setTitle(updatedMusicSet.getTitle());
            musicSet.setComposer(updatedMusicSet.getComposer());
            musicSet.setArranger(updatedMusicSet.getArranger());
            musicSet.setSuitableForTraining(updatedMusicSet.getSuitableForTraining());
            return musicSetRepository.save(musicSet);
        } else {
            throw new RuntimeException("Music Set not found");
        }
    }

    /**
     * Retrieves all MusicSets associated with a specific band by its name.
     *
     * @param bandName The name of the band.
     * @return A list of MusicSets associated with the given band name.
     */
    public List<MusicSet> getMusicSetsByBand(String bandName) {
        return musicSetRepository.findByBandName(bandName);
    }

    /**
     * Adds a Band to an existing MusicSet by their respective IDs.
     *
     * @param MusicSetId The ID of the MusicSet to which the Band should be added.
     * @param BandId The ID of the Band to add to the MusicSet.
     * @return The updated MusicSet with the Band added.
     * @throws RuntimeException If the MusicSet or Band is not found, or if the MusicSet is not suitable for training.
     */
    public MusicSet addBandToMusicSet(Long MusicSetId, Long BandId) {
        Optional<MusicSet> musicSetOpt = musicSetRepository.findById(MusicSetId);
        Optional<Band> bandOpt = bandRepository.findById(BandId);

        if (musicSetOpt.isPresent() && bandOpt.isPresent()) {
            MusicSet musicSet = musicSetOpt.get();
            Band band = bandOpt.get();
            if(!musicSet.getSuitableForTraining() && band.getName().equals("Training")){
                throw new RuntimeException("Music Set not suitable for the training band");
            }
            else{
                musicSet.getBands().add(band);
                return musicSetRepository.save(musicSet);
            }
        }
        else{
            throw new RuntimeException("Band not found");
        }
    }

    /**
     * Removes all associated Bands from a MusicSet.
     *
     * @param musicSetId The ID of the MusicSet from which the Bands should be removed.
     * @throws RuntimeException If there is an error while updating the MusicSet.
     */
    public void deletePractice(Long musicSetId) {
        try {
            Optional<MusicSet> musicSetOpt = musicSetRepository.findById(musicSetId);
            if(musicSetOpt.isPresent()){
                MusicSet musicSet = musicSetOpt.get();
                if (musicSet.getBands() != null) {
                    Set<Band> musicSetBands = musicSet.getBands();
                    musicSetBands.clear();
                    musicSet.setBands(musicSetBands);
                    musicSetRepository.save(musicSet);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error putting music set into storage: " + e.getMessage());
        }
    }


}
