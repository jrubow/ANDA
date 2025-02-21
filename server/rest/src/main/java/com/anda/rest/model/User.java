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
public class User {
    @Id
    private String username;
    private String password;
    private String email;
    private String address;
    private int phone_number;
    private int share_location;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, String address, int phone_number, int share_location, int work_id, int verified) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.share_location = share_location;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public int getShare_location() {
        return share_location;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public void setShare_location(int share_location) {
        this.share_location = share_location;
    }
}
