package com.anda.rest.repository;

import com.anda.rest.model.Report;
// import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;

/**
 * Interface for User repository
 * @author Josh Rubow (jrubow)
 */

public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("SELECT reports FROM Report reports WHERE reports.device_id = :deviceId")
    List<Report> findReportsByDeviceId(@Param("device_id") Integer deviceId);

    // @Modifying
    // @Transactional
    // @Query("UPDATE User u SET u.login_attempts = 0 WHERE u.username = :username")
    // void resetLoginAttempts(String username);

    // @Query("SELECT u.login_attempts FROM User u WHERE u.username = :username")
    // int getLoginAttempts(String username);
}