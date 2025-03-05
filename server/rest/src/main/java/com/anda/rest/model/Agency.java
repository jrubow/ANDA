package com.anda.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Agency object
 * @author Gleb Bereziuk (gl3bert)
 */

@Entity
@Table(name="agencies")
public class Agency {
    @Id
    private int agency_id;
    private String name;
    private String address;
    private String phone_number;
    private String email;

    public Agency() {

    }

    public Agency(int agency_id, String name, String address, String phone_number, String email) {
        this.agency_id = agency_id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
