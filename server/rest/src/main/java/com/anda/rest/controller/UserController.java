package com.anda.rest.controller;

import com.anda.rest.model.LoginRequest;
import com.anda.rest.model.User;
import com.anda.rest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * User API processing
 * @author Gleb Bereziuk
 */

@RestController
@RequestMapping("/api")
public class UserController {

    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userService.checkUserCredentials(loginRequest.getUsername(), loginRequest.getPassword());

        if (user == null) {
            return ResponseEntity.status(401).body("INCORRECT USERNAME");
        }

        if (user.getUsername() == null && user.getLogin_attempts() <= 5) {
            return ResponseEntity.status(401).body("INCORRECT PASSWORD " + user.getLogin_attempts());
        }

        if (user.getLogin_attempts() > 5) {
            return ResponseEntity.status(401).body("MAXIMUM PASSWORD ATTEMPTS REACHED");
        }

        user.setPassword(null);
        user.setLogin_attempts(0);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/login")
    public ResponseEntity<String> showLoginMessage() {
        return ResponseEntity.ok("Use POST with JSON to log in.");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean isCreated = userService.registerUser(user);
        return isCreated ? ResponseEntity.ok("USER REGISTERED") : ResponseEntity.status(400).body("USER ALREADY EXISTS");
    }

    @GetMapping("/register")
    public ResponseEntity<String> showRegisterMessage() {
        return ResponseEntity.ok("Use POST with JSON to register.");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, Object> updates) {
        try {
            boolean isUpdated = userService.updateUserDetails(updates);
            return isUpdated ? ResponseEntity.ok("USER UPDATED") : ResponseEntity.status(404).body("USER NOT FOUND");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @GetMapping("{username}")
    public User getUserDetails(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @GetMapping()
    public List<User> getAllUserDetails(String username) {
        return userService.getAllUsers();
    }

    @PostMapping
    public String createUserDetails(@RequestBody User user) {
        userService.createUser(user);
        return "USER CREATED";
    }

    @PutMapping
    public String updateUserDetails(@RequestBody User user) {
        userService.updateUser(user);
        return "USER UPDATED";
    }

    @DeleteMapping("{username}")
    public String deleteUserDetails(@PathVariable String username) {
        userService.deleteUser(username);
        return "USER DELETED";
    }
}