package com.anda.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * System Admin class.
 * For ANDA admins/developers (us) for global management.
 * @author Gleb Bereziuk (gl3bert)
 */

@Entity
@Table(name="system_admins")
public class SystemAdmin {
    @Id
    @Column(name="sysadmin_id")
    private int sysAdminId;
    @Column(name="password")
    private String password;
    @Column(name="verified")
    private boolean verified;
    @Column(name="email")
    private String email;
    @Column(name="first")
    private String first;
    @Column(name="last")
    private String last;

    // Empty constructor.
    public SystemAdmin() {}

    // Constructor for initializing.
    public SystemAdmin(String email, String first, String last) {
        this.email = email;
        this.first = first;
        this.last = last;
        this.password = null;
        this.verified = false;
    }

    // Constructor for password setting and authentication.
    public SystemAdmin(int sysAdminId, String password) {
        this.sysAdminId = sysAdminId;
        this.password = password;
    }

    // Getters and setters.
    public int getSysAdminId() { return sysAdminId; }
    public void setSysAdminId(int sysAdminId) { this.sysAdminId = sysAdminId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirst() { return first; }
    public void setFirst(String first) { this.first = first; }
    public String getLast() { return last; }
    public void setLast(String last) { this.last = last; }

}
