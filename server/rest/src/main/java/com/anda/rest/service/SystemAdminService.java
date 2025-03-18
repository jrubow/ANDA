package com.anda.rest.service;

import com.anda.rest.model.SystemAdmin;

/**
 * Interface for SystemAdmin service.
 * Declares implemented methods.
 * @author Gleb Bereziuk (gl3bert)
 */
public interface SystemAdminService {
    SystemAdmin getSystemAdmin(int sysAdminId);
    boolean newSystemAdminEntry(SystemAdmin systemAdmin);
    boolean existsByEmail(String email);
    boolean verifySystemAdmin(int sysAdminId);
    boolean validateEmail(String email);
    boolean validatePassword(String password);
    boolean setPassword(int sysAdminId, String password);
    boolean login(SystemAdmin loginRequest);
}
