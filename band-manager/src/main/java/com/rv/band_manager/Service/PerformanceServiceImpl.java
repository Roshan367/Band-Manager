package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.*;
import com.rv.band_manager.Repository.BandRepository;
import com.rv.band_manager.Repository.MusicSetRepository;
import com.rv.band_manager.Repository.PerformanceMemberRepository;
import com.rv.band_manager.Repository.PerformanceRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service implementation for managing performances and their associations with bands, music sets, and performance members.
 */
@Service
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final BandRepository bandRepository;
    private final PerformanceMemberRepository performanceMemberRepository;
    private final MusicSetRepository musicSetRepository;

    /**
     * Constructor for PerformanceServiceImpl.
     *
     * @param performanceRepository The repository used to interact with the Performance data.
     * @param bandRepository The repository used to interact with the Band data.
     * @param performanceMemberRepository The repository used to interact with the PerformanceMember data.
     * @param musicSetRepository The repository used to interact with the MusicSet data.
     */
    public PerformanceServiceImpl(PerformanceRepository performanceRepository, BandRepository bandRepository,
                                  PerformanceMemberRepository performanceMemberRepository,
                                  MusicSetRepository musicSetRepository) {
        this.performanceRepository = performanceRepository;
        this.bandRepository = bandRepository;
        this.performanceMemberRepository = performanceMemberRepository;
        this.musicSetRepository = musicSetRepository;
    }

    /**
     * Retrieves all performances from the performance repository.
     *
     * @return A list of all performances.
     */
    @Override
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    /**
     * Retrieves a specific performance by its ID.
     *
     * @param id The ID of the performance to retrieve.
     * @return An Optional containing the performance if found, or an empty Optional if not found.
     */
    @Override
    public Optional<Performance> getPerformanceById(Long id) {
        return performanceRepository.findById(id);
    }

    /**
     * Retrieves all performances associated with a particular band by the band's ID.
     *
     * @param bandId The ID of the band whose performances are being retrieved.
     * @return A list of performances associated with the given band.
     */
    @Override
    public List<Performance> getPerformanceByBand(Long bandId) {
        return performanceRepository.findByBand(bandId);
    }

    /**
     * Saves a new performance to the performance repository.
     *
     * @param performance The performance to save.
     * @return The saved performance.
     */
    @Override
    public Performance savePerformance(Performance performance) {
        return performanceRepository.save(performance);
    }

    /**
     * Updates an existing performance's details.
     *
     * @param id The ID of the performance to update.
     * @param updatedPerformance The updated performance details.
     * @return The updated performance.
     * @throws RuntimeException If the performance with the given ID is not found.
     */
    @Override
    public Performance updatePerformance(Long id, Performance updatedPerformance) {
        Optional<Performance> performanceOptional = performanceRepository.findById(id);
        if (performanceOptional.isPresent()) {
            Performance performance = performanceOptional.get();
            performance.setLocation(updatedPerformance.getLocation());
            performance.setDate(updatedPerformance.getDate());
            performance.setTime(updatedPerformance.getTime());
            performance.setMusicSets(updatedPerformance.getMusicSets());
            return performanceRepository.save(performance);
        } else {
            throw new RuntimeException("Performance not found");
        }
    }

    /**
     * Deletes a performance by its ID.
     * This will also remove the performance from associated bands, music sets, and performance members.
     *
     * @param id The ID of the performance to delete.
     * @throws RuntimeException If the performance with the given ID is not found.
     */
    @Override
    public void deletePerformance(Long id) {
        Optional<Performance> performanceOptional = performanceRepository.findById(id);
        if (performanceOptional.isPresent()) {
            Performance performance = performanceOptional.get();

            //Remove performance reference from associated bands
            Set<Band> bands = performance.getBands();
            for (Band band : bands) {
                band.getPerformances().remove(performance);
            }

            //Remove performance reference from associated music sets
            Set<MusicSet> musicSets = performance.getMusicSets();
            for (MusicSet musicSet : musicSets) {
                musicSet.getPerformances().remove(performance);
            }

            //Save the updated bands and music sets
            bandRepository.saveAll(bands);
            musicSetRepository.saveAll(musicSets);
            //Delete associated performance members
            performanceMemberRepository.deleteByPerformanceId(id);
            performanceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Performance not found");
        }
    }

    /**
     * Adds a band to a performance by associating the band with the performance.
     * Also adds all users from the band as performance members for the performance.
     *
     * @param performanceId The ID of the performance to which the band should be added.
     * @param bandId The ID of the band to add to the performance.
     * @throws IllegalArgumentException If the performance or band is not found.
     */
    @Override
    public void addBandToPerformance(Long performanceId, Long bandId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found"));
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new IllegalArgumentException("Band not found"));

        if (!performance.getBands().contains(band)) {
            performance.getBands().add(band);
            band.getPerformances().add(performance);
            performanceRepository.save(performance);
            bandRepository.save(band);
        }
        Set<User> users = band.getUsers();

        for (User user : users) {
            PerformanceMemberId primaryKey = new PerformanceMemberId(user.getId(), bandId, performanceId);

            if (!performanceMemberRepository.existsById(primaryKey)) {
                PerformanceMember performanceMember = new PerformanceMember();
                performanceMember.setPerformanceMemberId(primaryKey);
                performanceMember.setUser(user);
                performanceMember.setBand(band);
                performanceMember.setPerformance(performance);
                performanceMemberRepository.save(performanceMember);
            }
        }
    }

    /**
     * Removes a band from a performance by disassociating the band from the performance.
     * Also removes all performance members related to the band from the performance.
     *
     * @param performanceId The ID of the performance from which the band should be removed.
     * @param bandId The ID of the band to remove from the performance.
     * @throws IllegalArgumentException If the performance or band is not found.
     */
    @Override
    public void removeBandFromPerformance(Long performanceId, Long bandId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found"));
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new IllegalArgumentException("Band not found"));

        if (performance.getBands().contains(band)) {
            performance.getBands().remove(band);
            band.getPerformances().remove(performance);

            Set<User> users = band.getUsers();
            for (User user : users) {
                PerformanceMemberId primaryKey = new PerformanceMemberId(user.getId(), bandId, performanceId);
                performanceMemberRepository.deleteById(primaryKey);
            }

            performanceRepository.save(performance);
            bandRepository.save(band);
        }
    }
}
