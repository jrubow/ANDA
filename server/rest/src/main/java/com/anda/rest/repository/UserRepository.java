package com.anda.rest.repository;

import com.anda.rest.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/**
 * Interface for User repository
 * @author Gleb Bereziuk (gl3bert)
 */

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.login_attempts = u.login_attempts + 1 WHERE u.username = :username")
    void incrementLoginAttempts(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.login_attempts = 0 WHERE u.username = :username")
    void resetLoginAttempts(String username);

    @Query("SELECT u.login_attempts FROM User u WHERE u.username = :username")
    int getLoginAttempts(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.last_login = :lastLogin WHERE u.username = :username")
    void updateLastLogin(String username, LocalDateTime lastLogin);
}