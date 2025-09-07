package com.rv.band_manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rv.band_manager.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
