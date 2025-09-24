package com.rv.band_manager.Service;

import com.rv.band_manager.Model.Miscellaneous;

import java.util.List;
import java.util.Optional;

public interface MiscellaneousService {
    List<Miscellaneous> getAllMiscellaneous();
    Miscellaneous saveMiscellaneous(Miscellaneous item);
    Miscellaneous updateMiscellaneous(Long id, Miscellaneous updatedItem);
    Optional<Miscellaneous> getMiscellaneousById(Long id);
    Optional<Miscellaneous> getMiscellaneousByNameAndMake(String name, String make);
    void setAvailableMiscellaneousQuantity();
    void deleteMiscellaneous(Long id);
}
