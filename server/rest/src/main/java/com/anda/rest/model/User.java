package com.anda.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Embedded;
import jakarta.persistence.Table;

/**
 * User class
 * @author Gleb Bereziuk (gl3bert), @author Jinhoo Yoon (juhg9543)
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
    private int verified;


    @Embedded
    private Coordinates coords;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.share_location = 0; // base setting configuration is to not share location
        this.coords = new Coordinates(); // sets long and lat values to non-valid coordinates
    }

    public User(String username, String password, String email, String address, int phone_number, int share_location, int work_id, int verified) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        if (this.share_location != 0 && this.share_location != 1) { // if input is not valid, just set share_location to false
            this.share_location = 0;
        } else {
            this.share_location = share_location;
        }
        if (this.share_location == 0) {
            this.coords = new Coordinates();
        }
    }

    public User(String username, String password, String email, String address, int phone_number, int share_location, int work_id, int verified, double longitude, double latitude) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.share_location = share_location;
        if (this.share_location != 0 && this.share_location != 1) { // if input is not valid, just set share_location to false
            this.share_location = 0;
        } else {
            this.share_location = share_location;
        }
        if (this.share_location == 0) {
            this.coords = new Coordinates();
        }
        coords.setCoordinates(longitude, latitude);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPhone_number() {
        return this.phone_number;
    }

    public int getShare_location() {
        return this.share_location;
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

    public void setCoordinates(double longitude, double latitude) {
        this.coords.setCoordinates(longitude, latitude);
    }

    public void setCoordinates(Coordinates coords) { // for if passed via another device/user perhaps
        this.coords = coords;
    }
}
