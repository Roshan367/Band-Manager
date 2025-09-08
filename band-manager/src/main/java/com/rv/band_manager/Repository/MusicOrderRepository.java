package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rv.band_manager.Model.MusicOrder;
import com.rv.band_manager.Model.MusicPart;

import java.util.List;
import java.util.Optional;

public interface MusicOrderRepository extends JpaRepository<MusicOrder, Long> {
    //Finds all MusicOrder entities associated with a specific owner by their userId, status, child
    List<MusicOrder> findByOwnerId(Long userId);
    List<MusicOrder> findByStatusAndOwnerId(String status, Long userId);
    List<MusicOrder> findByStatusAndChildId(String status, Long childId);
    List<MusicOrder> findByStatus(String status);

    //Finds all adult MusicOrder entities
    @Query("SELECT mo FROM MusicOrder mo WHERE mo.owner.id = :ownerId AND mo.child IS NULL")
    List<MusicOrder> findAdultMusicOrders(@Param("ownerId") Long ownerId);

    //Finds all child MusicOrder entities
    @Query("SELECT mo FROM MusicOrder mo WHERE mo.owner.id = :ownerId AND mo.child IS NOT NULL")
    List<MusicOrder> findChildMusicOrders(@Param("ownerId") Long ownerId);

}
