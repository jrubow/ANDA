package com.anda.rest.controller;

import com.anda.rest.model.*;
import com.anda.rest.service.AgencyService;
import com.anda.rest.service.EmailService;
import com.anda.rest.service.UserService;
import com.anda.rest.service.AdminService;
import com.anda.rest.service.FilterService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller for User and Admin objects
 * @author Gleb Bereziuk (gl3bert)
 */

@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserService userService;
    private final FilterService filterService;
    private final AdminService adminService;
    private final EmailService emailService;
    private final AgencyService agencyService;

    public AccountController(UserService userService, AdminService adminService, EmailService emailService, AgencyService agencyService, FilterService filterService) {
        this.userService = userService;
        this.adminService = adminService;
        this.emailService = emailService;
        this.agencyService = agencyService;
        this.filterService = filterService;
    }

    @GetMapping("/login-guest")
    public ResponseEntity<String> showGuestMessage() { return ResponseEntity.ok("ANDA Guest Page"); }

    @PostMapping("/login-guest")
    public ResponseEntity<?> guest(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.ok().body("Welcome, Guest!");
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/login")
    public ResponseEntity<String> showLoginMessage() {
        return ResponseEntity.ok("ANDA Login Page");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication authentication, @RequestBody LoginRequest loginRequest) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
        }
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

            if (!agencyService.existsById(acc.getAgency_id())) {
                return ResponseEntity.status(400).body("AGENCY DOES NOT EXIST");
            }

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
                emailService.sendEmail(agency.getEmail(), "ANDA: Verify New Agency Admin Account",
                                "This is an automatic message from ANDA system.\n" +
                                "A new admin has just registered with " + agency.getName() + ".\n\n" +
                                "Name: " + admin.getLast_name() + ", " + admin.getFirst_name() + "\n" +
                                "Username: " + admin.getUsername() + "\n" +
                                "Email: " + admin.getEmail() + "\n" +
                                "Phone number: " + admin.getPhone_number() + "\n\n" +
                                "To verify this admin, follow the link below\n" +
                                "http://localhost:8080/api/verify/" + admin.getUsername() + "\n\n\n" +
                                "Generated: " + now);
                emailService.sendEmail(admin.getEmail(), "ANDA: Registration confirmation",
                                "This is an automatic message from ANDA system.\n" +
                                admin.getFirst_name() + ", you have successfully signed up as an admin for " + agency.getName() + ".\n\n" +
                                "A verification email has been sent to your agency. Once a representative verifies your account, " +
                                "you will get a confirmation email. At that point, you will have access to your account.\n\n" +
                                "Contact your agency with any questions.\n\n" +
                                "Generated: " + now);
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

            if (isCreated) {
                LocalDateTime now = LocalDateTime.now();
                emailService.sendEmail(user.getEmail(), "ANDA: Registration confirmation",
                                "This is an automatic message from ANDA system.\n\n" +
                                user.getFirst_name() + ", thank you for registering with ANDA. " +
                                "We are happy to have you with us!\n\n" +
                                "Generated: " + now);

            }

            return isCreated ? ResponseEntity.ok("USER REGISTERED")
                    : ResponseEntity.status(400).body("USER ALREADY EXISTS");
        }
    }

    @GetMapping("/verify/{username}")
    public ResponseEntity<String> verifyAdmin(@PathVariable String username) {
        boolean isVerified = adminService.verifyAdmin(username);
        if (isVerified) {
            Admin admin = adminService.getByUsername(username);
            LocalDateTime now = LocalDateTime.now();
            emailService.sendEmail(admin.getEmail(), "ANDA: Verification confirmation",
                            "This is an automatic message from ANDA system.\n\n" +
                            admin.getFirst_name() + ", you have been verified by your agency. " +
                            "You now have access to you account!\n\n" +
                            "Generated: " + now);
            return ResponseEntity.ok("ANDA: Admin " + admin.getLast_name() + ", " + admin.getFirst_name() +
                    " at agency " + admin.getAgency_id() + " verified successfully!");
        } else {
            return ResponseEntity.status(400).body("ADMIN NOT FOUND OR IS ALREADY VERIFIED");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAccount(Authentication authentication, @RequestBody Map<String, Object> updates) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
        }
        try {
            String username = (String) updates.get("username");
            if (username == null) {
                return ResponseEntity.status(400).body("USERNAME REQUIRED FOR UPDATING ACCOUNT");
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

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAccount(Authentication authentication, @RequestBody LoginRequest request) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
        }

        User user = userService.getByUsername(request.getUsername());
        boolean isDeleted = false;
        if (user != null) {
            isDeleted = userService.deleteUser(request.getUsername(), request.getPassword());
        }

        if (!isDeleted) {
            Admin admin = adminService.getByUsername(request.getUsername());
            if (admin != null) {
                isDeleted = adminService.deleteAdmin(request.getUsername(), request.getPassword());
            }
            if (isDeleted) {
                LocalDateTime now = LocalDateTime.now();
                emailService.sendEmail(admin.getEmail(), "ANDA: Account deleted",
                                "This is an automatic message from ANDA system.\n\n" +
                                admin.getFirst_name() + ", your account has been deleted successfully. " +
                                "Your data is no longer stored with us nor with your agency within ANDA. We will miss you!..\n\n" +
                                "Generated: " + now);
                return ResponseEntity.ok("ADMIN ACCOUNT DELETED SUCCESSFULLY");
            }
            return ResponseEntity.status(400).body("INVALID CREDENTIALS OR USERNAME NOT FOUND");
        }
        else {
            LocalDateTime now = LocalDateTime.now();
            emailService.sendEmail(user.getEmail(), "ANDA: Account deleted",
                            "This is an automatic message from ANDA system.\n\n" +
                            user.getFirst_name() + ", your account has been deleted successfully. " +
                            "Your data is no longer stored with us. We will miss you!..\n\n" +
                            "Generated: " + now);

            return ResponseEntity.ok("USER ACCOUNT DELETED SUCCESSFULLY");
        }
    }

    @GetMapping("/filter/username")
    public ResponseEntity<?> getFilterByUsername(Authentication authentication, @RequestBody User user) {
        try {
            // Extract deviceId from the request body
            if (authentication != null && authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
                return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
            }
            String username = user.getUsername();
            System.out.println(username);
            if (username == null) {
                return ResponseEntity.badRequest()
                        .body("ERROR : username SHOULD NOT BE NULL");
            }
            // Retrieve filters using the username
            List<Filter> filters = filterService.getFiltersByUsername(username);

            if (filters == null || filters.size() == 0) {
                return ResponseEntity.badRequest()
                        .body("ERROR : user has no filters");
            }

            // Spring Boot will automatically convert the List<Report> to JSON
            return ResponseEntity.ok(filters);
        } catch (Exception e) {
            // Return a JSON formatted error message in case of exception
            return ResponseEntity.status(500)
                    .body("ERROR : COULD NOT RETRIEVE USER'S FILTER");
        }
    }
}