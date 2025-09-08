package com.rv.band_manager.Service;

import com.rv.band_manager.Model.PerformanceMember;

import java.util.List;
import java.util.Optional;

public interface PerformanceMemberService {
    Optional<PerformanceMember> findByUserIdAndBandIdAndPerformanceId(Long userId, Long bandId, Long performanceId);
    List<PerformanceMember> findByPerformanceIdAndAvailability(Long performanceId, Boolean availability);
    PerformanceMember savePerformanceMember(PerformanceMember performanceMember);
    PerformanceMember updatePerformanceMember(Long userId, Long bandId, Long performanceId, PerformanceMember performanceMember);
}
