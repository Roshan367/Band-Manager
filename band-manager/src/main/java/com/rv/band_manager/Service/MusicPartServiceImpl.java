package com.rv.band_manager.Service;

import org.springframework.stereotype.*;

import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Model.MusicPart;
import com.rv.band_manager.Model.MusicSet;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Repository.MusicPartRepository;
import com.rv.band_manager.Repository.MusicSetRepository;
import com.rv.band_manager.Repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for managing MusicParts in the application.
 * Provides functionality to retrieve, create, update, and associate MusicParts with MusicSets and users.
 */
@Service
public class MusicPartServiceImpl implements MusicPartService {
    private final MusicPartRepository musicPartRepository;
    private final MusicSetRepository musicSetRepository;
    private final UserRepository userRepository;

    /**
     * Constructor a new instance  of MusicPartServiceImpl with the specified repositories.
     *
     * @param musicPartRepository the repository for managing MusicPart data access
     * @param musicSetRepository the repository for managing MusicSet data access
     * @param userRepository the repository for managing User data access
     */
    public MusicPartServiceImpl(MusicPartRepository musicPartRepository, MusicSetRepository musicSetRepository,
                                UserRepository userRepository) {
        this.musicPartRepository = musicPartRepository;
        this.musicSetRepository = musicSetRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all MusicPart records from the database.
     *
     * @return a list of all MusicParts
     */
    public List<MusicPart> getAllMusicParts() {
        return musicPartRepository.findAll();
    }

    /**
     * Retrieves a specific MusicPart by its ID.
     *
     * @param id the ID of the MusicPart
     * @return an Optional containing the MusicPart if found, or empty if not
     */
    public Optional<MusicPart> getMusicPartById(Long id) {
        return musicPartRepository.findById(id);
    }

    /**
     * Creates a new MusicPart and associates it with a MusicSet.
     *
     * @param musicSetId the ID of the MusicSet to associate with
     * @param musicPart  the MusicPart to create
     * @return the saved MusicPart
     * @throws RuntimeException if the MusicSet with the given ID is not found
     */
    public MusicPart createMusicPart(Long musicSetId, MusicPart musicPart) {
        MusicSet musicSet = musicSetRepository.findById(musicSetId)
                .orElseThrow(() -> new RuntimeException("MusicSet not found with id: " + musicSetId));
        musicPart.setMusicSet(musicSet);
        return musicPartRepository.save(musicPart);
    }

    /**
     * Saves a MusicPart to the repository.
     *
     * @param musicPart the MusicPart to save
     * @return the saved MusicPart
     */
    public MusicPart saveMusicPart(MusicPart musicPart) {
        return musicPartRepository.save(musicPart);
    }

    /**
     * Updates an existing MusicPart by its ID.
     *
     * @param id               the ID of the MusicPart to update
     * @param updatedMusicPart the updated MusicPart data
     * @return the updated MusicPart
     * @throws RuntimeException if the MusicPart with the given ID is not found
     */
    public MusicPart updateMusicPart(Long id, MusicPart updatedMusicPart) {
        Optional<MusicPart> musicPartOptional = musicPartRepository.findById(id);
        if (musicPartOptional.isPresent()) {
            MusicPart musicPart = musicPartOptional.get();
            // Update the details of the existing MusicPart.
            musicPart.setPartName(updatedMusicPart.getPartName());
            return musicPartRepository.save(musicPart);
        } else {
            throw new RuntimeException("Music Part not found");
        }
    }


    /**
     * Retrieves all MusicParts associated with a specific MusicSet.
     *
     * @param musicSetId the ID of the MusicSet
     * @return a list of MusicParts associated with the MusicSet
     */
    public List<MusicPart> getPartsByMusicSetId(Long musicSetId) {
        return musicPartRepository.findByMusicSetId(musicSetId);
    }

    /**
     * Retrieves MusicParts needed by a user based on their associated bands and MusicSets.
     *
     * @param userId the ID of the user
     * @return a list of MusicParts the user needs
     * @throws RuntimeException if the User with the given ID is not found
     */
    public List<MusicPart> getUserMusicPartNeeded(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Band> bands = user.getBands();
        if (bands.isEmpty()) {
            return new ArrayList<>();
        }

        //Collect all MusicSets from the user's bands and then collect all MusicParts
        Set<MusicSet> musicSets = bands.stream()
                .flatMap(band -> band.getMusicSets().stream())
                .collect(Collectors.toSet());

        return musicSets.stream()
                .flatMap(musicSet -> musicSet.getMusicParts().stream())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific MusicPart based on its name, MusicSet title, and arranger.
     *
     * @param musicPartName    the name of the MusicPart
     * @param musicSetTitle    the title of the associated MusicSet
     * @param musicSetArranger the arranger of the associated MusicSet
     * @return an Optional containing the MusicPart if found, or empty if not
     */
    public Optional<MusicPart> getMusicPartForOrder(String musicPartName, String musicSetTitle,
                                                    String musicSetArranger) {
        return musicPartRepository.findSpecificMusicPart(musicPartName, musicSetTitle, musicSetArranger);
    }

    /**
     * Retrieves all MusicParts associated with a user.
     *
     * @param ownerId the ID of the user
     * @return a list of MusicParts associated with the user
     */
    public List<MusicPart> getUserMusicPart(Long ownerId) {
        return musicPartRepository.findAllByOwnerIdAndFulfilledOrders(ownerId);
    }

    /**
     * Retrieves all MusicParts associated with a child.
     *
     * @param childId the ID of the child
     * @return a list of MusicParts associated with the child
     */
    public List<MusicPart> getChildMusicPart(Long childId) {
        return musicPartRepository.findAllByChildIdAndFulfilledOrders(childId);
    }


}
