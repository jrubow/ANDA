package com.anda.rest.service;

import com.anda.rest.model.User;

import java.time.LocalDateTime;
import java.util.List;
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
    boolean deleteUser(String username);
    User getByUsername(String username);
    boolean existsByEmail(String email);
    List<User> getAllUsers();
    List<User> getInactiveUsers();
    void warnUser(String username);
    void warnReset(String username);
}