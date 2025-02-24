package com.anda.rest.model;

import java.util.UUID;

public class APIKey {
    private String key;
    private String username;
    private boolean isGuest;
    private boolean generated; // keeps track of if a key has already been generated so you don't generate more than once.
    // May later become extraneous due to revocation of keys, etc.

    public APIKey() {
        this.isGuest = true;
    }

    public APIKey(String username) {
        this.username = username; // mostly just to make sure we know if it is a user or not
    }

    void generateKey() { // creates the key
        this.key = UUID.randomUUID().toString();
    }

    String getKey() {
        return this.key;
    }

    String getUsername() {
        return this.username;
    }

    void setUsername(String username) {
        this.username = username;
    }

}
