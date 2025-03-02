package com.anda.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * User class
 * @author Gleb Bereziuk (gl3bert)
 */

@Entity
@Table(name="users")
public class User extends Account {

    public User(String username, int loginAttempts) {
        super(username, loginAttempts);
    }

    public User() {
        super();
    }
}