package com.anda.rest.controller;

import com.anda.rest.model.SystemAdmin;
import com.anda.rest.model.User;
import com.anda.rest.service.AdminService;
import com.anda.rest.service.EmailService;
import com.anda.rest.service.SystemAdminService;
import com.anda.rest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for SystemAdmin class.
 * Specifies API handling.
 * @author Gleb Bereziuk (gl3bert)
 */

@RestController
@RequestMapping("api/sysadmin")
public class SystemAdminController {
    // Set up controllers.
    private final SystemAdminService systemAdminService;
    private final UserService userService;
    private final AdminService adminService;
    private final EmailService emailService;
    public SystemAdminController(SystemAdminService systemAdminService, UserService userService,
                                 AdminService adminService, EmailService emailService) {
        this.systemAdminService = systemAdminService;
        this.userService = userService;
        this.adminService = adminService;
        this.emailService = emailService;
    }

    // Placeholder
    @GetMapping("/request")
    public ResponseEntity<String> showRegisterMessage() {
        return ResponseEntity.ok("ANDA System Admin Account Request Page");
    }

    // Process request
    @PostMapping("/request")
    public ResponseEntity<?> registerSysAdmin(@RequestBody SystemAdmin systemAdmin) {
        if (!systemAdminService.validateEmail(systemAdmin.getEmail())) {
            return ResponseEntity.badRequest().body("INVALID EMAIL");
        }
        if (systemAdminService.existsByEmail(systemAdmin.getEmail()) ||
            userService.existsByEmail(systemAdmin.getEmail()) ||
            adminService.existsByEmail(systemAdmin.getEmail())) {
            return ResponseEntity.status(400).body("EMAIL ALREADY TAKEN");
        }
        boolean isCreated = systemAdminService.newSystemAdminEntry(systemAdmin);
        if (isCreated) {
            LocalDateTime now = LocalDateTime.now();
            emailService.sendEmail(systemAdmin.getEmail(), "ANDA: System Admin Account Requested",
                    "This is an automated message from ANDA system.\n\n" +
                    systemAdmin.getFirst() + ", you have successfully requested a system admin account. " +
                    "ANDA staff will review your request shortly. Once verified, you will get a follow-up " +
                    "email with your login ID and a link to set your password.\n\n" +
                    "Generated: " + now);
        }
        return isCreated ? ResponseEntity.ok("SYSADMIN REQUESTED")
                : ResponseEntity.status(400).body("SYSADMIN ALREADY EXISTS");
    }

    // Verify sysadmin.
    @PostMapping("/verify/{systemAdminId}")
    public ResponseEntity<?> verifySysAdmin(@PathVariable int systemAdminId) {
        SystemAdmin systemAdmin = systemAdminService.getSystemAdmin(systemAdminId);
        if (systemAdmin == null) {
            return ResponseEntity.badRequest().body("SYSADMIN NOT FOUND");
        }
        boolean isVerified = systemAdminService.verifySystemAdmin(systemAdminId);
        if (isVerified) {
            LocalDateTime now = LocalDateTime.now();
            emailService.sendEmail(systemAdmin.getEmail(), "ANDA: System Admin Account Verified",
                    "This is an automated message from ANDA system.\n\n" +
                            systemAdmin.getFirst() + ", your account has been verified.\n\n" +
                            "Your assigned ID is: " + systemAdmin.getSysAdminId() +
                            ".\n\nOnce you set your password, you will gain access to your account. " +
                            "Follow the link to set your password: https://localhost:3000/api/sysadmin/password\n\n" +
                            "Generated: " + now);
        }
        return isVerified ? ResponseEntity.ok("SYSADMIN VERIFIED")
                : ResponseEntity.status(400).body("SYSADMIN ALREADY VERIFIED");
    }

    // Set password
    @PostMapping("/password")
    public ResponseEntity<?> setPassword(@RequestBody SystemAdmin passwordRequest) {
        SystemAdmin systemAdmin = systemAdminService.getSystemAdmin(passwordRequest.getSysAdminId());
        if (systemAdmin == null) {
            return ResponseEntity.badRequest().body("SYSADMIN NOT FOUND");
        }
        if (!systemAdmin.isVerified()) {
            return ResponseEntity.badRequest().body("SYSADMIN NOT YET VERIFIED");
        }
        if (systemAdmin.getPassword() != null) {
            return ResponseEntity.badRequest().body("PASSWORD ALREADY SET");
        }
        boolean isSet = systemAdminService.setPassword(passwordRequest.getSysAdminId(), passwordRequest.getPassword());
        if (isSet) {
            LocalDateTime now = LocalDateTime.now();
            emailService.sendEmail(systemAdmin.getEmail(), "ANDA: System Admin Account Password Set",
                    "This is an automated message from ANDA system.\n\n" +
                            systemAdmin.getFirst() + ", your account password has been set successfully. " +
                            "You now have access to your system admin account.\n\n" +
                            "Generated: " + now);
        }
        return isSet ? ResponseEntity.ok("PASSWORD SET")
                : ResponseEntity.status(400).body("INVALID PASSWORD");
    }

    // Authenticate
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SystemAdmin loginRequest) {
        if (loginRequest.getSysAdminId() == 0 || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("LOGIN FIELDS MISSING");
        }
        boolean success = systemAdminService.login(loginRequest);
        if (success) {
            SystemAdmin systemAdmin = systemAdminService.getSystemAdmin(loginRequest.getSysAdminId());
            systemAdmin.setPassword(null);
            return ResponseEntity.ok(systemAdmin);
        }
        else {
            return ResponseEntity.badRequest().body("INVALID CREDENTIALS");
        }
    }

    // Get the list of all users.
    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get the list of inactive users.
    @GetMapping("/users/inactive")
    public List<User> getInactiveUsers() {
        return userService.getInactiveUsers();
    }

    // Send deletion warning.
    @PostMapping("/users/warn/{username}")
    public ResponseEntity<?> warnUser(@PathVariable String username) {
        User user = userService.getByUsername(username);
        if (user != null && !user.isWarning_sent()) {
            userService.warnUser(username);
            LocalDateTime now = LocalDateTime.now();
            emailService.sendEmail(user.getEmail(), "ANDA: Account Termination Warning",
                    "This is an automated message from ANDA system.\n\n" +
                            user.getFirst_name() + ", your account has been marked for deletion due to inactivity " +
                            "per ANDA policies. Your account will be deleted after " + now.plusDays(7) +
                            ". Log in to ANDA to avoid termination!\n\n" +
                            "Generated: " + now);
            return ResponseEntity.ok("USER WARNED");
        }
        return ResponseEntity.badRequest().body("USER NOT FOUND OR WARNING ALREADY SENT");
    }

    // Delete user.
    @PostMapping("/users/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User user = userService.getByUsername(username);
        if (user != null) {
            if (userService.deleteUser(username)) {
                LocalDateTime now = LocalDateTime.now();
                emailService.sendEmail(user.getEmail(), "ANDA: Account Deleted",
                        "This is an automated message from ANDA system.\n\n" +
                                user.getFirst_name() + ", your account has been permanently deleted for inactivity " +
                                "per ANDA policies. We are sad to see you go, but feel free to register again later!\n\n" +
                                "Generated: " + now);
                return ResponseEntity.ok("USER DELETED");
            }
            ResponseEntity.status(400).body("ERROR DELETING USER");
        }
        return ResponseEntity.badRequest().body("USER NOT FOUND");
    }
}
