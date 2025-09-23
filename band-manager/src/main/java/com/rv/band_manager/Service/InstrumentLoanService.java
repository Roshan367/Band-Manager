package com.rv.band_manager.Service;

import com.rv.band_manager.Model.InstrumentLoan;
import com.rv.band_manager.Model.Instrument;
import com.rv.band_manager.Model.User;

import java.util.List;
import java.util.Optional;

public interface InstrumentLoanService {
    List<InstrumentLoan> getAllInstrumentLoans();
    List<InstrumentLoan> getAllInstrumentLoansNotReturned();
    List<InstrumentLoan> getAllInstrumentLoansReturned();
    List<InstrumentLoan> getUserInstrumentLoansNotReturned(Long userId);
    List<InstrumentLoan> getUserInstrumentLoansReturned(Long userId);
    InstrumentLoan createInstrumentLoan(User user, Instrument instrument);
    InstrumentLoan returnInstrumentLoan(InstrumentLoan updatedInstrumentLoan);
    Optional<InstrumentLoan> getInstrumentLoanById(Long id);
    void deleteInstrumentLoan(Long id);
    List<InstrumentLoan> getInstrumentLoansByUserId(Long userId);
    List<Instrument> getInstrumentsNotLoaned();
    List<Instrument> getInstrumentsLoaned();
}
