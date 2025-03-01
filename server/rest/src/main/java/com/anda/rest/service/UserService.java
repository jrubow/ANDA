package com.anda.rest.service;

import com.anda.rest.model.User;

import java.util.List;
import java.util.Map;

/**
 * Interface for User services
 * @author Gleb Bereziuk (gl3bert)
 */

public interface UserService {
    public String createUser(User user);
    public String updateUser(User user);
    public String deleteUser(String username);
    public User getUser(String username);
    public List<User> getAllUsers();
    public User checkUserCredentials(String user, String password);
    boolean registerUser(User user);
    public boolean updateUserDetails(Map<String, Object> updates);
}