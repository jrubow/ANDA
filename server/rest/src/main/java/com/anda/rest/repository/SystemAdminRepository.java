package com.anda.rest.repository;

import com.anda.rest.model.SystemAdmin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Respository interface for SystemAdmin class.
 * Handles SQL requests.
 * @author Gleb Bereziuk (gl3bert)
 */

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, String> {
    SystemAdmin findByEmail(String email);

    @Query("SELECT s FROM SystemAdmin s WHERE s.sysAdminId = :sysAdminId")
    SystemAdmin findById(int sysAdminId);

    @Query("SELECT MAX(s.sysAdminId) FROM SystemAdmin s")
    Integer findMaxId();
}
