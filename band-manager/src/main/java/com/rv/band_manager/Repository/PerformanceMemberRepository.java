package com.rv.band_manager.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rv.band_manager.Model.PerformanceMember;
import com.rv.band_manager.Model.PerformanceMemberId;

import java.util.List;
import java.util.Optional;

public interface PerformanceMemberRepository extends JpaRepository<PerformanceMember, PerformanceMemberId> {
    Optional<PerformanceMember> findByUserIdAndBandIdAndPerformanceId(Long userId, Long bandId, Long performanceId);
    List<PerformanceMember> findByPerformanceIdAndAvailability(Long performanceId, Boolean availability);

    //Deletes all PerformanceMember entities associated with a specific performance
    @Modifying
    @Transactional
    @Query("DELETE FROM PerformanceMember pm WHERE pm.performance.id = :performanceId")
    void deleteByPerformanceId(@Param("performanceId") Long performanceId);

}
