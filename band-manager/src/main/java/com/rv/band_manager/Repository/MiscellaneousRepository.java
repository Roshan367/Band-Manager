package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.Miscellaneous;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MiscellaneousRepository extends JpaRepository<Miscellaneous, Long> {
  //Finds all miscellaneous not on loan
    @Query("""
    SELECT m
    FROM Miscellaneous m
    LEFT JOIN MiscellaneousLoan ml ON ml.miscellaneous.id = m.id
    GROUP BY m.id
    HAVING m.quantity - COALESCE(SUM(ml.quantity), 0) > 0    
    """)
    List<Miscellaneous> findMiscellaneousNotLoaned();
    
    @Query("""
    SELECT m
    FROM Miscellaneous m
    LEFT JOIN MiscellaneousLoan ml ON ml.miscellaneous.id = m.id
    GROUP BY m.id
    HAVING m.quantity - COALESCE(SUM(ml.quantity), 0) > 0
    """)
    List<Miscellaneous> findMiscellaneousLoaned();

    Optional<Miscellaneous> findByNameAndMake(String name, String make);

}
