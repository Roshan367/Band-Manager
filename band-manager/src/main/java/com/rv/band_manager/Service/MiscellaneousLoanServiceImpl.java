package com.rv.band_manager.Service;

import org.springframework.stereotype.Service;
import com.rv.band_manager.Model.MiscellaneousLoan;
import com.rv.band_manager.Model.Miscellaneous;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Repository.MiscellaneousRepository;
import com.rv.band_manager.Repository.MiscellaneousLoanRepository;
import com.rv.band_manager.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
/**
 * Implementation of the MiscellaneousLoanService interface.
 * Provides logic for managing musical Miscellaneous loans, including CRUD operations.
 */
@Service
public class MiscellaneousLoanServiceImpl implements MiscellaneousLoanService {

    LocalDate localDate = LocalDate.now();
    private final MiscellaneousLoanRepository miscellaneousLoanRepository;
    private final MiscellaneousRepository miscellaneousRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of MiscellaneousLoanServiceImpl with the specified repository.
     *
     * @param misceallneousLoanRepository the repository used for miscellaneous loan data access
     */
    public MiscellaneousLoanServiceImpl(MiscellaneousLoanRepository miscellaneousLoanRepository, MiscellaneousRepository miscellaneousRepository,
        UserRepository userRepository) {
        this.miscellaneousLoanRepository = miscellaneousLoanRepository;
        this.miscellaneousRepository = miscellaneousRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all miscellaneous loans from the repository.
     *
     * @return a list of all miscellaneous loans
     */
    public List<MiscellaneousLoan> getAllMiscellaneousLoans() {
        return miscellaneousLoanRepository.findAll();
    }

    /**
     * Creates a new miscellaneous loan to the repository.
     *
     * @param userId the unique ID of the user
     * @param miscellaneousId the unique ID of the miscellaneous
     * @return the saved miscellaneous loan
     */
    public MiscellaneousLoan createMiscellaneousLoan(User user, Miscellaneous miscellaneous, Integer quantity) {
        MiscellaneousLoan miscellaneousLoan = new MiscellaneousLoan();
        miscellaneousLoan.setUser(user);
        miscellaneousLoan.setMiscellaneous(miscellaneous);
        miscellaneousLoan.setDate(localDate);
        miscellaneousLoan.setQuantity(quantity);
        miscellaneousLoan.setReturned(false);
        return miscellaneousLoanRepository.save(miscellaneousLoan);
    }

    /**
     * Retrieves an miscellaneous loan by its unique identifier.
     *
     * @param id the unique ID of the miscellaneous loan
     * @return an Optional containing the miscellaneous loan if found, or empty if not
     */
    public Optional<MiscellaneousLoan> getMiscellaneousLoanById(Long id) {
        return miscellaneousLoanRepository.findById(id);
    }

    /**
     * Returns an miscellaneous loan in the repository.
     *
     * @param id the ID of the miscellaneous loan to update
     * @param returnedMiscellaneousLoan the new miscellaneous loan data to update with
     * @return the returned miscellaneous loan
     * @throws IllegalArgumentException if the miscellaneous loan is not found
     */
    public MiscellaneousLoan returnMiscellaneousLoan(MiscellaneousLoan miscellaneousLoan) {
        miscellaneousLoan.setReturned(true);
        return miscellaneousLoanRepository.save(miscellaneousLoan);
    }

    /**
     * Retrieves all Miscellaneous Loans for a specific user.
     *
     * @param userId the unique ID of the user
     * @return a list of miscellaneous loans associated with the user
     */
    public List<MiscellaneousLoan> getMiscellaneousLoansByUserId(Long userId) {
        return miscellaneousLoanRepository.findByUserId(userId);
    }

    /**
     * Retrieves Miscellaneous Loan, which have been returned.
     *
     * @return a list of miscellaneous loans with returned value false
     */
    public List<MiscellaneousLoan> getAllMiscellaneousLoansReturned() {
        return miscellaneousLoanRepository.findByReturned(true);
    }

    /**
     * Retrieves Miscellaneous Loan, which have not been returned.
     *
     * @return a list of miscellaneous loans with returned value false
     */
    public List<MiscellaneousLoan> getAllMiscellaneousLoansNotReturned() {
        return miscellaneousLoanRepository.findByReturned(false);
    }

    /**
     * Retrieves Miscellaneous Loan for a user by their ID, which have not been returned.
     *
     * @return a list of miscellaneous loans with returned with value true
     */
    public List<MiscellaneousLoan> getUserMiscellaneousLoansReturned(Long userId) {
        return miscellaneousLoanRepository.findByUserIdAndReturned(userId, true);
    }

    /**
     * Retrieves Miscellaneous Loan for a user by their ID, which have not been returned.
     *
     * @param userId the unique ID of the user
     * @return a list of music orders with the status "NOT_READY"
     */
    public List<MiscellaneousLoan> getUserMiscellaneousLoansNotReturned(Long userId) {
        return miscellaneousLoanRepository.findByUserIdAndReturned(userId, false);
    }

    public List<Miscellaneous> getMiscellaneousNotLoaned(){
      return miscellaneousRepository.findMiscellaneousNotLoaned();
    }

    public List<Miscellaneous> getMiscellaneousLoaned(){
      return miscellaneousRepository.findMiscellaneousLoaned();
    }

    /**
     * Deletes an existing miscellaneous Loan from the repository.
     *
     * @param id the ID of the miscellaneous loan to delete
     * @throws IllegalArgumentException if the miscellaneous is not found
     */
    public void deleteMiscellaneousLoan(Long id) {
        MiscellaneousLoan miscellaneousLoan = miscellaneousLoanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Miscellaneous loan not found"));
        miscellaneousLoanRepository.delete(miscellaneousLoan);
    }

}
