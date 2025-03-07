package com.anda.rest.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Class for Relay Devices
 * @author Josh Rubow (jrubow)
 */

@Entity
@Table(name = "relay_devices")
public class RelayDevice extends Device {
    private int sentinel_id;
    private int sentinel_connection;

    // Default Constructor
    public RelayDevice() {};

    // Main Constructor
    public RelayDevice(int sensor_id, double latitude, double longitude, double battery_life,
                          String last_online, LocalDateTime deployed_date, int deployed,
                          int is_connected, int num_connected_devices, int sentinel_id, int sentinel_connection) {
        super(sensor_id, latitude, longitude, battery_life, last_online, deployed_date, deployed);
        this.sentinel_id = sentinel_id;
        this.sentinel_connection = sentinel_connection;
    }

    // Getter / Setters
    public int getSentinelId() {
        return this.sentinel_id;
    }

    public void setSentinelId(int sentinel_id) {
        this.sentinel_id = sentinel_id;
    }

    public int getSentinelConnection() {
        return this.sentinel_connection;
    }

    public void setSentinelConnection(int sentinel_connection) {
        if (sentinel_connection == 0 || sentinel_connection == 1) {
            this.sentinel_connection = sentinel_connection;
        } else {
            throw new IllegalArgumentException("Parameter must be 0 or 1");
        }
    }

    
}
