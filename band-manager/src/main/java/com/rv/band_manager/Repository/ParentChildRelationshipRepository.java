package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.ParentChildRelationship;
import com.rv.band_manager.Model.User;

import java.util.*;

public interface ParentChildRelationshipRepository extends JpaRepository<ParentChildRelationship, Long>{
    List<ParentChildRelationship> findByParent(User parent);
    Optional<ParentChildRelationship> findByChild(User child);
}
