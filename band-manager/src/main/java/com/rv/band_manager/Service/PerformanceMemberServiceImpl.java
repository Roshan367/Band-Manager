package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.PerformanceMember;
import com.rv.band_manager.Repository.PerformanceMemberRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing PerformanceMembers and their associations with users, bands, and performances.
 */
@Service
public class PerformanceMemberServiceImpl implements PerformanceMemberService {
    private final PerformanceMemberRepository performanceMemberRepository;

    /**
     * Constructor for PerformanceMemberServiceImpl.
     *
     * @param performanceMemberRepository The repository used to interact with the PerformanceMember data.
     */
    public PerformanceMemberServiceImpl(PerformanceMemberRepository performanceMemberRepository) {
        this.performanceMemberRepository = performanceMemberRepository;
    }

    /**
     * Retrieves a PerformanceMember by the given user ID, band ID, and performance ID.
     *
     * @param userId The ID of the user associated with the PerformanceMember.
     * @param bandId The ID of the band associated with the PerformanceMember.
     * @param performanceId The ID of the performance associated with the PerformanceMember.
     * @return An Optional containing the PerformanceMember if found, or an empty Optional if not found.
     */
    @Override
    public Optional<PerformanceMember> findByUserIdAndBandIdAndPerformanceId(Long userId, Long bandId, Long performanceId) {
        // Query the repository for a PerformanceMember with the given parameters.
        return performanceMemberRepository.findByUserIdAndBandIdAndPerformanceId(userId, bandId, performanceId);
    }

    /**
     * Retrieves a list of PerformanceMembers for a specific performance and their availability status.
     *
     * @param performanceId The ID of the performance for which PerformanceMembers are being retrieved.
     * @param availability The availability status (true or false) of the PerformanceMembers.
     * @return A list of PerformanceMembers matching the given performance ID and availability status.
     */
    @Override
    public List<PerformanceMember> findByPerformanceIdAndAvailability(Long performanceId, Boolean availability) {
        return performanceMemberRepository.findByPerformanceIdAndAvailability(performanceId, availability);
    }

    /**
     * Saves a new PerformanceMember to the repository.
     *
     * @param performanceMember The PerformanceMember to save.
     * @return The saved PerformanceMember.
     */
    @Override
    public PerformanceMember savePerformanceMember(PerformanceMember performanceMember) {
        return performanceMemberRepository.save(performanceMember);
    }

    /**
     * Updates an existing PerformanceMember by its user ID, band ID, and performance ID.
     *
     * @param userId The ID of the user associated with the PerformanceMember to update.
     * @param bandId The ID of the band associated with the PerformanceMember to update.
     * @param performanceId The ID of the performance associated with the PerformanceMember to update.
     * @param updatedPerformanceMember The updated PerformanceMember with new data.
     * @return The updated PerformanceMember.
     * @throws RuntimeException If the PerformanceMember with the given user ID, band ID, and performance ID is not found.
     */
    @Override
    public PerformanceMember updatePerformanceMember(Long userId, Long bandId, Long performanceId,
                                                     PerformanceMember updatedPerformanceMember) {
        Optional<PerformanceMember> performanceMemberOptional = performanceMemberRepository
                .findByUserIdAndBandIdAndPerformanceId(userId, bandId, performanceId);
        if (performanceMemberOptional.isPresent()) {
            PerformanceMember performanceMember = performanceMemberOptional.get();
            performanceMember.setAvailability(updatedPerformanceMember.getAvailability());
            return performanceMemberRepository.save(performanceMember);
        } else {
            throw new RuntimeException("Performance member not found");
        }
    }
}
