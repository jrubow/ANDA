package com.anda.rest.repository;

import com.anda.rest.model.SentinelDevice;
// import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Interface for Sentinel repository
 * @author Josh Rubow (jrubow)
 */

public interface SentinelDeviceRepository extends JpaRepository<SentinelDevice, Integer> {

    // @Modifying
    // @Transactional
    // @Query("UPDATE User u SET u.login_attempts = 0 WHERE u.username = :username")
    // void resetLoginAttempts(String username);

    // @Query("SELECT u.login_attempts FROM User u WHERE u.username = :username")
    // int getLoginAttempts(String username);
    List<SentinelDevice> findByAgencyId(int agencyId);
}