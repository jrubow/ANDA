package com.anda.rest.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/**
 * Superclass for managing User and Admin objects
 * @author Gleb Bereziuk (gl3bert)
 */

@MappedSuperclass
public abstract class Account {
    @Id
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String address;
    private String phone_number;
    private int share_location;
    private int login_attempts;
    private int temperature;
    private int snow;
    private int rain;
    private int humidity;
    private LocalDateTime last_login;
    private boolean warning_sent;

    public Account(String username, String password, String email, String first_name, String last_name,
                   String address, String phone_number, int share_location, int temperature, int snow, int rain, int humidity) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_number = phone_number;
        this.share_location = share_location;
        this.login_attempts = 0;
        this.temperature = temperature;
        this.snow = snow;
        this.rain = rain;
        this.humidity = humidity;
    }

    public Account(String username, int loginAttempts) {
        this.username = username;
        this.login_attempts = loginAttempts;
    }

    // For returning to the SystemAdmin table
    public Account(String username, String firstName, String lastName, String email, String phone, LocalDateTime lastLogin, boolean warned) {
        this.username = username;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.phone_number = phone;
        this.last_login = lastLogin;
        this.warning_sent = warned;
    }

    public Account() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getShare_location() {
        return share_location;
    }

    public void setShare_location(int share_location) {
        this.share_location = share_location;
    }

    public int getLogin_attempts() {
        return login_attempts;
    }

    public void setLogin_attempts(int login_attempts) {
        this.login_attempts = login_attempts;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getSnow() {
        return this.snow;
    }

    public void setSnow(int snow) {
        this.snow = snow;
    }

    public int getRain() {
        return this.rain;
    }

    public void setRain(int rain) {
        this.rain = rain;
    }

    public int getHumidity() {
        return this.humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public LocalDateTime getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDateTime last_login) {
        this.last_login = last_login;
    }

    public boolean isWarning_sent() {
        return warning_sent;
    }

    public void setWarning_sent(boolean warning_sent) {
        this.warning_sent = warning_sent;
    }
}