package com.anda.rest.service.impl;

import com.anda.rest.model.Admin;
import com.anda.rest.model.User;
import com.anda.rest.repository.UserRepository;
import com.anda.rest.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Service implementation for User
 * @author Gleb Bereziuk (gl3bert)
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().length() < 4) {
            throw new IllegalArgumentException("Username must be at least 4 characters long.");
        }

        if (user.getEmail() == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (user.getPhone_number() == null || !Pattern.matches("^\\d{10}$", user.getPhone_number().toString())) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }

        if (user.getPassword() == null || !Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", user.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, with one uppercase letter, one lowercase letter, one digit, and one special character.");
        }
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

        Set<String> allowedFields = Set.of("password", "email", "address", "phone_number", "share_location");

        for (String key : updates.keySet()) {
            if (!allowedFields.contains(key) && !key.equals("username")) {
                throw new IllegalArgumentException("Field '" + key + "' cannot be modified.");
            }
        }

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "password" -> {
                        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", (String) value)) {
                            throw new IllegalArgumentException("Password does not meet security requirements.");
                        }
                        existingUser.setPassword(BCrypt.hashpw((String) value, BCrypt.gensalt(12)));
                    }
                    case "email" -> {
                        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", (String) value)) {
                            throw new IllegalArgumentException("Invalid email format.");
                        }
                        existingUser.setEmail((String) value);
                    }
                    case "address" -> existingUser.setAddress((String) value);
                    case "phone_number" -> {
                        if (!Pattern.matches("^\\d{10}$", value.toString())) {
                            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
                        }
                        existingUser.setPhone_number((String) value);
                    }
                    case "share_location" -> existingUser.setShare_location((Integer) value);
                }
            }
        });

        userRepository.save(existingUser);
        return true;
    }

    @Override
    public User checkUserCredentials(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.incrementLoginAttempts(username);
            if (BCrypt.checkpw(password, user.getPassword())) {
                userRepository.resetLoginAttempts(username);
                return user;
            }
            return new User(null, userRepository.getLoginAttempts(username));
        }
        return null;
    }

    @Override
    public boolean registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        validateUser(user);

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

        userRepository.save(user);
        return true;
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    @Transactional
    public boolean deleteUser(String username, String password) {
        User user = checkUserCredentials(username, password);
        if (user != null) {
            if (user.getUsername() == null) {
                return false;
            }
            userRepository.delete(user);
            return true;
        }
        return false;
    }

}
