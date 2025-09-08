package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Model.MusicSet;

import java.util.List;

public interface MusicSetRepository  extends JpaRepository<MusicSet, Long> {
    //Retrieves a list of MusicSet entities associated with a specific band by the band's name
    @Query("SELECT m FROM MusicSet m JOIN m.bands b WHERE b.name = :bandName")
    List<MusicSet> findByBandName(@Param("bandName") String bandName);
}
