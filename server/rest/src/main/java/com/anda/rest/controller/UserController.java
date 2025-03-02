package com.anda.rest.controller;

import com.anda.rest.model.LoginRequest;
import com.anda.rest.model.Report;
import com.anda.rest.model.User;
import com.anda.rest.model.Filter;
import com.anda.rest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/guest")
    public ResponseEntity<String> guestAccess(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.ok("Welcome, Guest!");
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/user")
    public ResponseEntity<String> userAccess(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            return ResponseEntity.ok("Welcome, User!");
        }
        return ResponseEntity.status(403).body("Access Denied");
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

    @PostMapping("/user/filter")
    public ResponseEntity<String> guestFilterAccess(Authentication authentication, @RequestBody Filter filter) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            userService.createFilter(filter);
            return ResponseEntity.ok("Welcome, User!");
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @PostMapping("/user/report")
    public ResponseEntity<String> guestReportAccess(Authentication authentication, @RequestBody Report report) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            userService.createReport(report);
            return ResponseEntity.ok("Welcome, User!");
        }
        return ResponseEntity.status(403).body("Access Denied");
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