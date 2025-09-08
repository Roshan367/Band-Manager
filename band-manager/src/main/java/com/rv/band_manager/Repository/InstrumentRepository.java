package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
}
