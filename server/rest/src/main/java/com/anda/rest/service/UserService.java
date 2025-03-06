package com.anda.rest.service;

import com.anda.rest.model.User;

import java.util.List;
import java.util.Map;

/**
 * Service layer for User
 * @author Gleb Bereziuk (gl3bert)
 */

public interface UserService {
    public User checkUserCredentials(String user, String password);
    boolean registerUser(User user);
    public boolean updateUserDetails(Map<String, Object> updates);
    public boolean existsByUsername(String username);
    public boolean deleteUser(String username, String password);
}