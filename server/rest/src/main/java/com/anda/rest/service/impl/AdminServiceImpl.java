package com.anda.rest.service.impl;

import com.anda.rest.model.Admin;
import com.anda.rest.model.Agency;
import com.anda.rest.repository.AdminRepository;
import com.anda.rest.repository.AgencyRepository;
import com.anda.rest.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Service implementation for Admin
 * @author Gleb Bereziuk (gl3bert)
 */

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AgencyRepository agencyRepository;
    public AdminServiceImpl(AdminRepository adminRepository, AgencyRepository agencyRepository) {
        this.adminRepository = adminRepository;
        this.agencyRepository = agencyRepository;
    }

    private void validateAdmin(Admin admin) {
        if (admin.getUsername() == null || admin.getUsername().length() < 4) {
            throw new IllegalArgumentException("Username must be at least 4 characters long.");
        }
        if (admin.getEmail() == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", admin.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (admin.getPhone_number() == null || !Pattern.matches("^\\d{10}$", admin.getPhone_number().toString())) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }
        if (admin.getPassword() == null || !Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$", admin.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, with one uppercase letter, one lowercase letter, one digit, and one special character.");
        }

        Agency a = agencyRepository.findAgencyByAgency_id(admin.getAgency_id());
        if (a == null) {
            throw new IllegalArgumentException("Agency not found.");
        }
    }

    @Override
    public boolean registerAdmin(Admin admin) {
        if (adminRepository.findByUsername(admin.getUsername()) != null) {
            return false;
        }
        validateAdmin(admin);

        String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(12));
        admin.setPassword(hashedPassword);

        adminRepository.save(admin);
        return true;
    }

    @Override
    public Admin checkAdminCredentials(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            adminRepository.incrementLoginAttempts(username);
            if (BCrypt.checkpw(password, admin.getPassword())) {
                adminRepository.resetLoginAttempts(username);
                LocalDateTime now = LocalDateTime.now();
                adminRepository.updateLastLogin(username, now);
                return admin;
            }
            return new Admin(null, adminRepository.getLoginAttempts(username));
        }
        return null;
    }

    @Override
    public boolean updateAdminDetails(Map<String, Object> updates) {
        String username = (String) updates.get("username");
        if (username == null) {
            throw new IllegalArgumentException("Username is required for updating admin details.");
        }

        Admin existingAdmin = adminRepository.findByUsername(username);
        if (existingAdmin == null) {
            return false;
        }

        Set<String> allowedFields = Set.of("password", "email", "address", "phone_number", "share_location", "verified", "agency_id");

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
                        existingAdmin.setPassword(BCrypt.hashpw((String) value, BCrypt.gensalt(12)));
                    }
                    case "email" -> {
                        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", (String) value)) {
                            throw new IllegalArgumentException("Invalid email format.");
                        }
                        existingAdmin.setEmail((String) value);
                    }
                    case "address" -> existingAdmin.setAddress((String) value);
                    case "phone_number" -> {
                        if (!Pattern.matches("^\\d{10}$", value.toString())) {
                            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
                        }
                        existingAdmin.setPhone_number((String) value);
                    }
                    case "share_location" -> existingAdmin.setShare_location((Integer) value);
                    case "verified" -> existingAdmin.setVerified((Boolean) value);
                    case "agency_id" -> {
                        if ((Integer) value < 0) {
                            throw new IllegalArgumentException("Invalid agency ID");
                        }
                        existingAdmin.setAgency_id((Integer) value);
                    }
                }
            }
        });

        adminRepository.save(existingAdmin);
        return true;
    }

    public boolean existsByUsername(String username) {
        return adminRepository.findByUsername(username) != null;
    }

    @Override
    public boolean verifyAdmin(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            if (!admin.isVerified()) {
                admin.setVerified(true);
                adminRepository.save(admin);
                return true;
            }
        }
        return false;
    }

    @Override
    public Admin getByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public int getLoginAttempts(String username) {
        return adminRepository.getLoginAttempts(username);
    }

    @Override
    public boolean deleteAdmin(String username, String password) {
        Admin admin = checkAdminCredentials(username, password);
        if (admin != null ) {
            if (admin.getUsername() == null) {
                return false;
            }
            adminRepository.delete(admin);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (adminRepository.findByEmail(email) != null) {
            return true;
        }
        return false;
    }
}
