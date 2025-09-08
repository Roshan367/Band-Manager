package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.InstrumentLoan;

public interface InstrumentLoanRepository extends JpaRepository<InstrumentLoan, Long> {
}
