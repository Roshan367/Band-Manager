package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.InstrumentLoan;
import com.rv.band_manager.Model.Instrument;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Repository.InstrumentRepository;
import com.rv.band_manager.Repository.InstrumentLoanRepository;
import com.rv.band_manager.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
/**
 * Implementation of the InstrumentLoanService interface.
 * Provides logic for managing musical instrument loans, including CRUD operations.
 */
@Service
public class InstrumentLoanServiceImpl implements InstrumentLoanService {

    LocalDate localDate = LocalDate.now();
    private final InstrumentLoanRepository instrumentLoanRepository;
    private final InstrumentRepository instrumentRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of InstrumentLoanServiceImpl with the specified repository.
     *
     * @param instrumentLoanRepository the repository used for instrument data access
     */
    public InstrumentLoanServiceImpl(InstrumentLoanRepository instrumentLoanRepository, InstrumentRepository instrumentRepository,
        UserRepository userRepository) {
        this.instrumentLoanRepository = instrumentLoanRepository;
        this.instrumentRepository = instrumentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all instrument loans from the repository.
     *
     * @return a list of all instrument loans
     */
    public List<InstrumentLoan> getAllInstrumentLoans() {
        return instrumentLoanRepository.findAll();
    }

    /**
     * Creates a new instrument loan to the repository.
     *
     * @param userId the unique ID of the user
     * @param instrumentId the unique ID of the instrument
     * @return the saved instrument loan
     */
    public InstrumentLoan createInstrumentLoan(User user, Instrument instrument) {
        InstrumentLoan instrumentLoan = new InstrumentLoan();
        instrumentLoan.setUser(user);
        instrumentLoan.setInstrument(instrument);
        instrumentLoan.setDate(localDate);
        instrumentLoan.setReturned(false);
        return instrumentLoanRepository.save(instrumentLoan);
    }

    /**
     * Retrieves an instrument loan by its unique identifier.
     *
     * @param id the unique ID of the instrument loan
     * @return an Optional containing the instrument loan if found, or empty if not
     */
    public Optional<InstrumentLoan> getInstrumentLoanById(Long id) {
        return instrumentLoanRepository.findById(id);
    }

    /**
     * Returns an instrument loan in the repository.
     *
     * @param id the ID of the instrument loan to update
     * @param returnedInstrumentLoan the new instrument data to update with
     * @return the returned instrument loan
     * @throws IllegalArgumentException if the instrument loan is not found
     */
    public InstrumentLoan returnInstrumentLoan(Long id, InstrumentLoan returnedInstrumentLoan) {
        InstrumentLoan instrumentLoan = instrumentLoanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrument loan not found"));

        instrumentLoan.setReturned(true);
        return instrumentLoanRepository.save(instrumentLoan);
    }

    /**
     * Retrieves all Instrument Loans for a specific user.
     *
     * @param userId the unique ID of the user
     * @return a list of instrument loans associated with the user
     */
    public List<InstrumentLoan> getInstrumentLoansByUserId(Long userId) {
        return instrumentLoanRepository.findByUserId(userId);
    }

    /**
     * Retrieves Instrument Loan, which have been returned.
     *
     * @return a list of instrument loans with returned value false
     */
    public List<InstrumentLoan> getAllInstrumentLoansReturned() {
        return instrumentLoanRepository.findByReturned(true);
    }

    /**
     * Retrieves Instrument Loan, which have not been returned.
     *
     * @return a list of instrument loans with returned value false
     */
    public List<InstrumentLoan> getAllInstrumentLoansNotReturned() {
        return instrumentLoanRepository.findByReturned(false);
    }

    /**
     * Retrieves Instrument Loan for a user by their ID, which have not been returned.
     *
     * @return a list of instrument loans with returned with value true
     */
    public List<InstrumentLoan> getUserInstrumentLoansReturned(Long userId) {
        return instrumentLoanRepository.findByUserIdAndReturned(userId, true);
    }

    /**
     * Retrieves Instrument Loan for a user by their ID, which have not been returned.
     *
     * @param userId the unique ID of the user
     * @return a list of music orders with the status "NOT_READY"
     */
    public List<InstrumentLoan> getUserInstrumentLoansNotReturned(Long userId) {
        return instrumentLoanRepository.findByUserIdAndReturned(userId, false);
    }

    public List<Instrument> getInstrumentsNotLoaned(){
      return instrumentRepository.findInstrumentsNotLoaned();
    }

    public List<Instrument> getInstrumentsLoaned(){
      return instrumentRepository.findInstrumentsLoaned();
    }

    /**
     * Deletes an existing instrument Loan from the repository.
     *
     * @param id the ID of the instrument loan to delete
     * @throws IllegalArgumentException if the instrument is not found
     */
    public void deleteInstrumentLoan(Long id) {
        InstrumentLoan instrumentLoan = instrumentLoanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrument loan not found"));
        instrumentLoanRepository.delete(instrumentLoan);
    }

}
