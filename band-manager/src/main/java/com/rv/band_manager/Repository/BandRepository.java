package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.Band;

import java.util.Optional;

public interface BandRepository extends JpaRepository<Band, Long> {
    Optional<Band> findByName(String bandName);
}
