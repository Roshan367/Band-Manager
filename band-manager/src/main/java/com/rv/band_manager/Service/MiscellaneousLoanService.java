package com.rv.band_manager.Service;

import com.rv.band_manager.Model.MiscellaneousLoan;


import java.util.List;
import java.util.Optional;

public interface MiscellaneousLoanService {
    List<MiscellaneousLoan> getAllMiscellaneousLoans();
    MiscellaneousLoan saveMiscellaneousLoan(MiscellaneousLoan miscellaneousLoan);
    MiscellaneousLoan updateMiscellaneousLoan(Long id, MiscellaneousLoan updatedMiscellaneousLoan);
    Optional<MiscellaneousLoan> getMiscellaneousLoanById(Long id);
    void deleteMiscellaneousLoan(Long id);
}
