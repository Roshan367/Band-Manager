package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Repository.BandRepository;

import java.util.*;

/**
 * Implementation of the BandService interface.
 * Provides business logic for managing bands, including retrieval by ID and name.
 */
@Service
public class BandServiceImpl implements BandService {
    private final BandRepository bandRepository;

    /**
     * Constructs a new instance of BandServiceImpl with the specified repository.
     *
     * @param bandRepository the repository used for band data access
     */
    public BandServiceImpl(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    /**
     * Retrieves all bands from the repository.
     *
     * @return a list of all bands
     */
    public List<Band> getAllBands() {
        return bandRepository.findAll();
    }

    /**
     * Retrieves a band by its unique identifier.
     *
     * @param id the unique ID of the band
     * @return an Optional containing the band if found, or empty if not
     */
    public Optional<Band> getBandById(Long id) {
        return bandRepository.findById(id);
    }

    /**
     * Retrieves a band by its name.
     *
     * @param bandName the name of the band
     * @return an Optional containing the band if found, or empty if not
     */
    public Optional<Band> getBandByName(String bandName) {
        return bandRepository.findByName(bandName);
    }

}
