package com.rv.band_manager.Service;

import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Model.User;

import java.util.List;
import java.util.Optional;

public interface BandService {
    List<Band> getAllBands();
    Optional<Band> getBandById(Long id);
    Optional<Band> getBandByName(String bandName);
}
