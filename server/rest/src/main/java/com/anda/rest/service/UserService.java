package com.anda.rest.service;

import com.anda.rest.model.User;
import com.anda.rest.model.Coordinates;
import com.anda.rest.model.WeatherEvent;

import java.util.List;

/**
 * Interface for User services
 * @author Gleb Bereziuk (gl3bert), @author Jinhoo Yoon (juhg9543)
 */

public interface UserService {
    public String createUser(User user);
    public String updateUser(String key, String username, User user);
    public String deleteUser(String key, String username);
    public User getUser(String key, String username);
    public Coordinates getCoordinates(String key, String username);
    public List<User> getAllUsers();
}
