package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.InstrumentNote;

public interface InstrumentNoteRepository extends JpaRepository<InstrumentNote, Long> {
}
