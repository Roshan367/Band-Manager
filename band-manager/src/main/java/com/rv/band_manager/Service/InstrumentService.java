package com.rv.band_manager.Service;

import com.rv.band_manager.Model.Instrument;

import java.util.List;
import java.util.Optional;

public interface InstrumentService {
    List<Instrument> getAllInstruments();
    Instrument saveInstrument(Instrument instrument);
    Instrument updateInstrument(Long id, Instrument updatedInstrument);
    Optional<Instrument> getInstrumentById(Long id);
    void deleteInstrument(Long id);

}
