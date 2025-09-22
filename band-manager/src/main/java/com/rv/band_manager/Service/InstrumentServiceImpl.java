package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.Instrument;
import com.rv.band_manager.Repository.InstrumentRepository;
import com.rv.band_manager.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the InstrumentService interface.
 * Provides logic for managing musical instruments, including CRUD operations.
 */
@Service
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentRepository instrumentRepository;

    /**
     * Constructs a new instance of InstrumentServiceImpl with the specified repository.
     *
     * @param instrumentRepository the repository used for instrument data access
     */
    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    /**
     * Retrieves all instruments from the repository.
     *
     * @return a list of all instruments
     */
    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    /**
     * Saves a new instrument to the repository.
     *
     * @param instrument the instrument to be saved
     * @return the saved instrument
     */
    public Instrument saveInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    /**
     * Retrieves an instrument by its unique identifier.
     *
     * @param id the unique ID of the instrument
     * @return an Optional containing the instrument if found, or empty if not
     */
    public Optional<Instrument> getInstrumentById(Long id) {
        return instrumentRepository.findById(id);
    }

    public Optional<Instrument> getInstrumentBySerialNumber(String serialNumber){
      return instrumentRepository.findBySerialNumber(serialNumber);
    }

    /**
     * Updates an existing instrument in the repository.
     *
     * @param id the ID of the instrument to update
     * @param updatedInstrument the new instrument data to update with
     * @return the updated instrument
     * @throws IllegalArgumentException if the instrument is not found
     */
    public Instrument updateInstrument(Long id, Instrument updatedInstrument) {
        Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrument not found"));

        //Update the Instrument's details with the new data
        instrument.setSerialNumber(updatedInstrument.getSerialNumber());
        instrument.setName(updatedInstrument.getName());
        instrument.setMake(updatedInstrument.getMake());
        return instrumentRepository.save(instrument);
    }

    /**
     * Deletes an existing instrument from the repository.
     *
     * @param id the ID of the instrument to delete
     * @throws IllegalArgumentException if the instrument is not found
     */
    public void deleteInstrument(Long id) {
        Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrument not found"));
        instrumentRepository.delete(instrument);
    }

}
