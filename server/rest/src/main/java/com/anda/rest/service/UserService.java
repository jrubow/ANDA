package com.anda.rest.service;

import com.anda.rest.model.User;

import java.util.Map;

/**
 * Service layer for User
 * @author Gleb Bereziuk (gl3bert)
 */

public interface UserService {
    User checkUserCredentials(String user, String password);
    boolean registerUser(User user);
    boolean updateUserDetails(Map<String, Object> updates);
    boolean existsByUsername(String username);
    boolean deleteUser(String username, String password);
    User getByUsername(String username);
    boolean existsByEmail(String email);
}