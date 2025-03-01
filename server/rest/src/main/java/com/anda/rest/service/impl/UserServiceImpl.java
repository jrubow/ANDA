package com.anda.rest.service.impl;

import com.anda.rest.model.User;
import com.anda.rest.repository.UserRepository;
import com.anda.rest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation for User services
 * @author Gleb Bereziuk (gl3bert)
 */

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

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
    public String updateUser(User user) {
        // TODO add necessary logic here
        userRepository.save(user);
        return "USER UPDATED IN DATABASE";
    }

    @Override
    public boolean updateUserDetails(Map<String, Object> updates) {
        String username = (String) updates.get("username");
        if (username == null) {
            throw new IllegalArgumentException("Username is required for updating user details.");
        }

        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            return false;
        }

        // Define allowed updatable fields
        Set<String> allowedFields = Set.of("password", "email", "address", "phone_number", "share_location");

        // Check for unauthorized fields
        for (String key : updates.keySet()) {
            if (!allowedFields.contains(key) && !key.equals("username")) {
                throw new IllegalArgumentException("Field '" + key + "' cannot be modified.");
            }
        }

        // Update only allowed fields
        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "password" -> existingUser.setPassword((String) value);
                    case "email" -> existingUser.setEmail((String) value);
                    case "address" -> existingUser.setAddress((String) value);
                    case "phone_number" -> existingUser.setPhone_number((Integer) value);
                    case "share_location" -> existingUser.setShare_location((Integer) value);
                }
            }
        });

        userRepository.save(existingUser);
        return true;
    }




    @Override
    public String deleteUser(String username) {
        // TODO add necessary logic here
        userRepository.deleteById(username);
        return "USER DELETED FROM DATABASE";
    }

    @Override
    public User getUser(String username) {
        // TODO add necessary logic here
        return userRepository.findById(username).get();
    }

    @Override
    public List<User> getAllUsers() {
        // TODO add necessary logic here
        return userRepository.findAll();
    }

    @Override
    public User checkUserCredentials(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.incrementLoginAttempts(username);

            if (user.getPassword().equals(password)) {
                userRepository.resetLoginAttempts(username);
                return user;
            }
            return new User(null, userRepository.getLoginAttempts(username));
        }
        return null;
    }

    public boolean registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        userRepository.save(user);
        return true;
    }
}