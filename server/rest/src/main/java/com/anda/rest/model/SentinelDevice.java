package com.anda.rest.model;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

/**
 * Class for Sentinel Devices
 * @author Josh Rubow (jrubow)
 */

@Entity
@Table(name = "sentinel_devices")
public class SentinelDevice extends Device {

    // Additional fields specific to SentinelDevice
    private int num_connected_devices;
    private String password;
    @Column(name="agency_id")
    private int agencyId;

    // Default constructor
    public SentinelDevice() {}

    // Parameterized constructor
    @JsonCreator
    public SentinelDevice(
            @JsonProperty("sensor_id") int sensor_id,
            @JsonProperty("latitude") double latitude,
            @JsonProperty("longitude") double longitude,
            @JsonProperty("battery_life") double battery_life,
            @JsonProperty("last_online") String last_online,
            @JsonProperty("deployed_date") LocalDateTime deployed_date,
            @JsonProperty("deployed") int deployed,
            @JsonProperty("is_connected") int is_connected,
            @JsonProperty("num_connected_devices") int num_connected_devices) {
        super(sensor_id, latitude, longitude, battery_life, last_online, deployed_date, deployed);
        this.num_connected_devices = num_connected_devices;
        this.password = generatePassword(8);
        this.agencyId = 0;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    public static String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    // Getters and Setters for additional fields
    public int getNumConnectedDevices() {
        return num_connected_devices;
    }

    public void setNumConnectedDevices(int num_connected_devices) {
        this.num_connected_devices = num_connected_devices;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAgencyId() {
        return this.agencyId;
    }

    public void setAgencyId(int agencyId) {
        System.out.println(agencyId);
        this.agencyId = agencyId;
    }
}
