package com.rv.band_manager.Service;

import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Model.Performance;

import java.util.List;
import java.util.Optional;

public interface PerformanceService {
    List<Performance> getAllPerformances();
    Optional<Performance> getPerformanceById(Long id);
    List<Performance> getPerformanceByBand(Long bandId);
    Performance savePerformance(Performance performance);
    Performance updatePerformance(Long id, Performance updatedPerformance);
    void deletePerformance(Long id);
    void addBandToPerformance(Long performanceId, Long bandId);
    void removeBandFromPerformance(Long performanceId, Long bandId);
}
