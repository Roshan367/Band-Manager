package com.rv.band_manager.Service;

import com.rv.band_manager.Model.MiscellaneousLoan;
import com.rv.band_manager.Model.Miscellaneous;
import com.rv.band_manager.Model.User;

import java.util.List;
import java.util.Optional;

public interface MiscellaneousLoanService {
    List<MiscellaneousLoan> getAllMiscellaneousLoans();
    List<MiscellaneousLoan> getAllMiscellaneousLoansNotReturned();
    List<MiscellaneousLoan> getAllMiscellaneousLoansReturned();
    List<MiscellaneousLoan> getUserMiscellaneousLoansNotReturned(Long userId);
    List<MiscellaneousLoan> getUserMiscellaneousLoansReturned(Long userId);
    MiscellaneousLoan createMiscellaneousLoan(User user, Miscellaneous miscellaneous, Integer quantity);
    MiscellaneousLoan returnMiscellaneousLoan(MiscellaneousLoan updatedMiscellaneousLoan);
    Optional<MiscellaneousLoan> getMiscellaneousLoanById(Long id);
    void deleteMiscellaneousLoan(Long id);
    List<MiscellaneousLoan> getMiscellaneousLoansByUserId(Long userId);
    List<Miscellaneous> getMiscellaneousNotLoaned();
    List<Miscellaneous> getMiscellaneousLoaned();
}
