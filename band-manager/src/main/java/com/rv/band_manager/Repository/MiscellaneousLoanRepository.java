package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.MiscellaneousLoan;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MiscellaneousLoanRepository extends JpaRepository<MiscellaneousLoan, Long> {
  List<MiscellaneousLoan> findByUserId(Long userId);
  List<MiscellaneousLoan> findByUserIdAndReturned(Long userId, Boolean returned);
  List<MiscellaneousLoan> findByReturned(Boolean returned);

  @Query("SELECT SUM(ml.quantity) FROM MiscellaneousLoan ml WHERE ml.miscellaneous.id = :miscId AND ml.returned=FALSE")
  Integer sumLoanedQuantityByMiscellaneousId(@Param("miscId") Long miscId);

}
