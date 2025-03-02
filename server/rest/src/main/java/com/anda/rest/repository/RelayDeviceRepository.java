package com.anda.rest.repository;

import com.anda.rest.model.RelayDevice;
// import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;

/**
 * Interface for User repository
 * @author Josh Rubow (jrubow)
 */

public interface RelayDeviceRepository extends JpaRepository<RelayDevice, Integer> {

    // @Modifying
    // @Transactional
    // @Query("UPDATE User u SET u.login_attempts = 0 WHERE u.username = :username")
    // void resetLoginAttempts(String username);

    // @Query("SELECT u.login_attempts FROM User u WHERE u.username = :username")
    // int getLoginAttempts(String username);
}