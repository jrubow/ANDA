package com.anda.rest.repository;

import com.anda.rest.model.Admin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface for User repository
 * @author Gleb Bereziuk (gl3bert)
 */

public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByUsername(String username);
    Admin findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Admin u SET u.login_attempts = u.login_attempts + 1 WHERE u.username = :username")
    void incrementLoginAttempts(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Admin u SET u.login_attempts = 0 WHERE u.username = :username")
    void resetLoginAttempts(String username);

    @Query("SELECT u.login_attempts FROM Admin u WHERE u.username = :username")
    int getLoginAttempts(String username);
}