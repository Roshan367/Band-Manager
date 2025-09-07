package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Model.Role;
import com.rv.band_manager.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Finds users by their email address if they have no parent relationship
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.parentRelationship IS NULL")
    Optional<User> findByEmailAndNoParent(String email);

    //Retrieves a list of users who have a parent relationship
    @Query("SELECT u FROM User u WHERE u.parentRelationship IS NOT NULL")
    List<User> findUsersWithParents();

    //Retrieves a list of users who do not have a parent relationship
    @Query("SELECT u FROM User u WHERE u.parentRelationship IS NULL")
    List<User> findUsersWithoutParents();

    //Finds all users who are children of a specific parent
    @Query("SELECT u FROM User u JOIN u.parentRelationship pr WHERE pr.parent.id = :parentId")
    List<User> findChildrenByParentId(Long parentId);

    //Finds a User by their full name
    Optional<User> findByFullName(String fullName);

    //Retrieves a list of users who are associated with a specific band
    @Query("SELECT u FROM User u JOIN u.bands b WHERE b.name = :bandName")
    List<User> findByBandName(@Param("bandName") String bandName);

    //Retrieves a list of Bands associated with a specific User
    @Query("SELECT u.bands FROM User u WHERE u.id = :userId")
    List<Band> findBandsByUserId(@Param("userId") Long userId);

    //Finds all users associated with a specific role
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findByRole(@Param("role") Role role);
}
