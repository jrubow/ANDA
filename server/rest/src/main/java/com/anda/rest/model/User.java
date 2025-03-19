package com.anda.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * User class
 * @author Gleb Bereziuk (gl3bert)
 */

@Entity
@Table(name="users")
public class User extends Account {

    public User(String username, String firstName, String lastName, String email, String phone, LocalDateTime lastLogin, boolean warned) {
        super(username, firstName, lastName, email, phone, lastLogin, warned);
    }

    public User(String username, int loginAttempts) {
        super(username, loginAttempts);
    }

    public User() {
        super();
    }
}