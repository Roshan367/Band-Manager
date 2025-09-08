package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.MusicSetNote;

public interface MusicSetNoteRepository extends JpaRepository<MusicSetNote, Long> {
}
