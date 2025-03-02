package com.anda.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends Account {
    private boolean verified;
    private int agency_id;

    public Admin(String username, int login_attempts) {
        super(username, login_attempts);
    }

    public Admin() {

    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }
}
