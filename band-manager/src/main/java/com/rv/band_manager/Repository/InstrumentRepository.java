package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.Instrument;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
  //Finds all instruments not on loan
    @Query("""
    SELECT i FROM Instrument i
    WHERE NOT EXISTS (
        SELECT il FROM InstrumentLoan il
        WHERE il.instrument = i AND il.returned = FALSE
    )
    """)
    List<Instrument> findInstrumentsNotLoaned();
    
  @Query("""
    SELECT i FROM Instrument i
    WHERE EXISTS (
        SELECT il FROM InstrumentLoan il
        WHERE il.instrument = i AND il.returned = FALSE
    )
    """)
    List<Instrument> findInstrumentsLoaned();
    Optional<Instrument> findBySerialNumber(String serialNumber);
}
