package com.anda.rest.service.impl;

import com.anda.rest.model.User;
import com.anda.rest.model.Coordinates;
import com.anda.rest.model.WeatherEvent;
import com.anda.rest.repository.UserRepository;
import com.anda.rest.repository.WeatherRepository;
import com.anda.rest.repository.APIKeyRepository;
import com.anda.rest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for User services
 * @author Gleb Bereziuk (gl3bert)
 */

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    APIKeyRepository apiKeyRepository;
    WeatherRepository weatherRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(User user) {
        // TODO add necessary logic here
        userRepository.save(user);
        return "USER ADDED TO DATABASE";
    }

    @Override
    public String updateUser(String key, String username, User user) {
        // TODO add necessary logic here

        if (userRepository.existsById(username)) {
            User temp = userRepository.findById(username).get();
            String tempKey = temp.getAPI_key();
            if (apiKeyRepository.findByKey(tempKey).getKey().equals(key)) { // if key and username match
                userRepository.save(user);
                return "USER DELETED";
            }
        }
        return "USER NOT FOUND OR INVALID CREDENTIALS";
    }

    @Override
    public String deleteUser(String key, String username) {
        // TODO add necessary logic here
        if (userRepository.existsById(username)) {
            User temp = userRepository.findById(username).get();
            String tempKey = temp.getAPI_key();
            if (apiKeyRepository.findByKey(tempKey).getKey().equals(key)) { // if key and username match
                userRepository.delete(temp);
                return "USER DELETED";
            }
        }
        return "USER NOT FOUND OR INVALID CREDENTIALS";
    }

    @Override
    public User getUser(String key, String username) {
        // TODO add necessary logic here
        if (userRepository.existsById(username)) {
            User temp = userRepository.findById(username).get();
            String tempKey = temp.getAPI_key();
            if (apiKeyRepository.findByKey(tempKey).getKey().equals(key)) { // if key and username match
                return userRepository.findById(username).get();
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        // TODO add necessary logic here
        return userRepository.findAll();
    }

    public Coordinates getCoordinates(String key, String username) {
        if (userRepository.existsById(username)) {
            User temp = userRepository.findById(username).get();
            String tempKey = temp.getAPI_key();
            if (apiKeyRepository.findByKey(tempKey).getKey().equals(key)) {
                return userRepository.findById(username).get().getCoords();
            }
        }
        return new Coordinates();
    }

    public WeatherEvent getWeatherEvent(int weatherId) {
        return weatherRepository.findByWeather_event_id(weatherId);
    }
}
