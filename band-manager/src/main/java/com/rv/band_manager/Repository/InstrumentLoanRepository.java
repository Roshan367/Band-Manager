package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.InstrumentLoan;

import java.util.List;
import java.util.Optional;

public interface InstrumentLoanRepository extends JpaRepository<InstrumentLoan, Long> {
  List<InstrumentLoan> findByUserId(Long userId);
  List<InstrumentLoan> findByUserIdAndReturned(Long userId, Boolean returned);
  List<InstrumentLoan> findByReturned(Boolean returned);
}
