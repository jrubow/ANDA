package com.anda.rest.controller;

import com.anda.rest.model.*;
import com.anda.rest.service.AgencyService;
import com.anda.rest.service.EmailService;
import com.anda.rest.service.UserService;
import com.anda.rest.service.AdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controller for User and Admin objects
 * @author Gleb Bereziuk (gl3bert)
 */

@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserService userService;
    private final AdminService adminService;
    private final EmailService emailService;
    private final AgencyService agencyService;

    public AccountController(UserService userService, AdminService adminService, EmailService emailService, AgencyService agencyService) {
        this.userService = userService;
        this.adminService = adminService;
        this.emailService = emailService;
        this.agencyService = agencyService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> showLoginMessage() {
        return ResponseEntity.ok("ANDA Login Page");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.checkUserCredentials(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            Admin admin = adminService.checkAdminCredentials(loginRequest.getUsername(), loginRequest.getPassword());

            if (admin == null) {
                return ResponseEntity.status(401).body("INCORRECT USERNAME");
            }

            if (admin.getUsername() == null && admin.getLogin_attempts() <= 5) {
                return ResponseEntity.status(401).body("INCORRECT PASSWORD " + admin.getLogin_attempts());
            }

            if (admin.getLogin_attempts() > 5) {
                return ResponseEntity.status(401).body("MAXIMUM PASSWORD ATTEMPTS REACHED");
            }

            if (!admin.isVerified()) {
                return ResponseEntity.status(401).body("ADMIN NOT YET VERIFIED");
            }

            admin.setPassword(null);
            admin.setLogin_attempts(0);
            return ResponseEntity.ok(admin);
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

    @GetMapping("/register")
    public ResponseEntity<String> showRegisterMessage() {
        return ResponseEntity.ok("ANDA Register Page");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody AccountFields acc) {

        if (userService.existsByUsername(acc.getUsername()) || adminService.existsByUsername(acc.getUsername())) {
            return ResponseEntity.status(400).body("USERNAME ALREADY EXISTS");
        }

        if (acc.getAgency_id() != null) {
            Admin admin = new Admin();
            admin.setUsername(acc.getUsername());
            admin.setPassword(acc.getPassword());
            admin.setEmail(acc.getEmail());
            admin.setAddress(acc.getAddress());
            admin.setPhone_number(acc.getPhone_number());
            admin.setShare_location(acc.getShare_location());
            admin.setFirst_name(acc.getFirst_name());
            admin.setLast_name(acc.getLast_name());
            admin.setAgency_id(acc.getAgency_id());

            boolean isCreated = adminService.registerAdmin(admin);

            if (isCreated)  {
                Agency agency = agencyService.getAgency(admin.getAgency_id());
                LocalDateTime now = LocalDateTime.now();
                emailService.sendEmail(admin.getEmail(), "ANDA: Verify New Agency Admin Account",
                        "### TEST ### TEST ### TEST ### TEST ### TEST ###\n\n" +
                                "This is an automatic message from ANDA system.\n" +
                                "A new admin has just registered with " + agency.getName() + "\n\n" +
                                "Name: " + admin.getLast_name() + ", " + admin.getFirst_name() +
                                "Username: " + admin.getUsername() + "\n" +
                                "Email: " + admin.getEmail() + "\n" +
                                "Phone number: " + admin.getPhone_number() + "\n\n" +
                                "To verify this admin, follow the link: ######################" + "\n\n\n" +
                                now);
            }

            return isCreated ? ResponseEntity.ok("ADMIN REGISTERED")
                    : ResponseEntity.status(400).body("ADMIN ALREADY EXISTS");
        } else {
            User user = new User();
            user.setUsername(acc.getUsername());
            user.setPassword(acc.getPassword());
            user.setEmail(acc.getEmail());
            user.setAddress(acc.getAddress());
            user.setPhone_number(acc.getPhone_number());
            user.setShare_location(acc.getShare_location());
            user.setFirst_name(acc.getFirst_name());
            user.setLast_name(acc.getLast_name());

            boolean isCreated = userService.registerUser(user);
            return isCreated ? ResponseEntity.ok("USER REGISTERED")
                    : ResponseEntity.status(400).body("USER ALREADY EXISTS");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAccount(@RequestBody Map<String, Object> updates) {
        try {
            String username = (String) updates.get("username");
            if (username == null) {
                return ResponseEntity.status(400).body("Username is required for updating account details.");
            }

            boolean isUpdated = false;

            if (userService.existsByUsername(username)) {
                isUpdated = userService.updateUserDetails(updates);
            } else if (adminService.existsByUsername(username)) {
                isUpdated = adminService.updateAdminDetails(updates);
            }

            return isUpdated ? ResponseEntity.ok("ACCOUNT UPDATED")
                    : ResponseEntity.status(404).body("ACCOUNT NOT FOUND");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}