package com.anda.rest.service.impl;

import com.anda.rest.model.Admin;
import com.anda.rest.model.SystemAdmin;
import com.anda.rest.repository.SystemAdminRepository;
import com.anda.rest.service.SystemAdminService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Implementation for SystemAdmin service.
 * Code for declared methods.
 * @author Gleb Bereziuk (gl3bert)
 */

@Service
public class SystemAdminServiceImpl implements SystemAdminService {
    // Repository setup.
    private final SystemAdminRepository systemAdminRepository;
    public SystemAdminServiceImpl(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    // New SystemAdmin entry
    @Override
    public boolean newSystemAdminEntry(SystemAdmin systemAdmin) {
        systemAdmin.setSysAdminId(getNextId());
        systemAdminRepository.save(systemAdmin);
        return true;
    }

    // Next ID assignment.
    public int getNextId() {
        Integer maxId = systemAdminRepository.findMaxId();
        return (maxId == null) ? 1000 : maxId + 1;
    }

    // Check if exists by email
    @Override
    public boolean existsByEmail(String email) {
        if (systemAdminRepository.findByEmail(email) != null) {
            return true;
        }
        return false;
    }

    // Verify
    @Override
    public boolean verifySystemAdmin(int sysAdminId) {
        SystemAdmin systemAdmin = systemAdminRepository.findById(sysAdminId);
        if (systemAdmin != null) {
            if (!systemAdmin.isVerified()) {
                systemAdmin.setVerified(true);
                systemAdminRepository.save(systemAdmin);
                return true;
            }
        }
        return false;
    }

    // Return by ID
    @Override
    public SystemAdmin getSystemAdmin(int sysAdminId) {
        return systemAdminRepository.findById(sysAdminId);
    }

    // Email validator.
    @Override
    public boolean validateEmail(String email) {
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
            return false;
        }
        return true;
    }

    // Password validator.
    @Override
    public boolean validatePassword(String password) {
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", password)) {
            return false;
        }
        return true;
    }

    // Password setter.
    @Override
    public boolean setPassword(int sysAdminId, String password) {
        if (validatePassword(password)) {
            SystemAdmin systemAdmin = getSystemAdmin(sysAdminId);
            systemAdmin.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
            systemAdminRepository.save(systemAdmin);
            return true;
        }
        return false;
    }

    // Logging in authentication.
    @Override
    public boolean login(SystemAdmin loginRequest) {
        if (systemAdminRepository.findById(loginRequest.getSysAdminId()) != null) {
            SystemAdmin systemAdmin = systemAdminRepository.findById(loginRequest.getSysAdminId());
            if (BCrypt.checkpw(loginRequest.getPassword(), systemAdmin.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
