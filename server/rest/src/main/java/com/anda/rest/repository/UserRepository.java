package com.anda.rest.repository;

import com.anda.rest.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for User repository
 * @author Gleb Bereziuk (gl3bert)
 */

public interface UserRepository extends JpaRepository<User, String> {
    // Search by username
    User findByUsername(String username);

    // Search by email
    User findByEmail(String email);

    // +1 login attempt for unsuccessful password entries
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.login_attempts = u.login_attempts + 1 WHERE u.username = :username")
    void incrementLoginAttempts(String username);

    // 0fy login attempts upon success
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.login_attempts = 0 WHERE u.username = :username")
    void resetLoginAttempts(String username);

    // Find login attempts by username
    @Query("SELECT u.login_attempts FROM User u WHERE u.username = :username")
    int getLoginAttempts(String username);

    // Change last login date upon successful entry
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.last_login = :lastLogin WHERE u.username = :username")
    void updateLastLogin(String username, LocalDateTime lastLogin);

    // Return all users with only the most crucial data -- TODO FIGURE OUT WHY NOT GOING TO THE RIGHT CONSTRUCTOR OR USE DTO
    @Query("SELECT new User(u.username, u.first_name, u.last_name, u.email, u.phone_number, u.last_login, u.warning_sent) FROM User u")
    List<User> findAllUsers();

    // Return users that are "inactive" -- TODO UPDATE THE TIMEFRAME
    @Query("SELECT new User(u.username, u.first_name, u.last_name, u.email, u.phone_number, u.last_login, u.warning_sent) " +
            "FROM User u WHERE u.last_login < :lastAcceptableDate")
    List<User> findInactiveUsers(@Param("lastAcceptableDate") LocalDateTime lastAcceptableDate);

    // Mark account as warned about upcoming deletion
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.warning_sent = true WHERE u.username = :username")
    void warningSent(String username);

    // Reset warning
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.warning_sent = false WHERE u.username = :username")
    void warningReset(String username);
}