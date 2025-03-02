package com.anda.rest.service;

import com.anda.rest.model.Admin;
import java.util.Map;

/**
 * Service layer for Admin
 * @author Gleb Bereziuk (gl3bert)
 */

public interface AdminService {
    boolean registerAdmin(Admin admin);
    Admin checkAdminCredentials(String username, String password);
    boolean updateAdminDetails(Map<String, Object> updates);
    public boolean existsByUsername(String username);
}
