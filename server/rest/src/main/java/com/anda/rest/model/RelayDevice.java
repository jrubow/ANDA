package com.anda.rest.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Class for Relay Devices
 * @author Josh Rubow (jrubow)
 */

@Entity
@Table(name = "relay_device")
public class RelayDevice extends Device {
    private int sentinel_id;

    // Default Constructor
    public RelayDevice() {};

    // Main Constructor
    public RelayDevice(int sensor_id, double latitude, double longitude, double battery_life,
                          LocalDateTime last_online, LocalDateTime deployed_date, int deployed,
                          int is_connected, int num_connected_devices, int sentinel_id) {
        super(sensor_id, latitude, longitude, battery_life, last_online, deployed_date, deployed);
        this.sentinel_id = sentinel_id;
    }

    // Getter / Setters
    public int getSentinel_id() {
        return this.sentinel_id;
    }

    public void setSentinel_id(int sentinel_id) {
        this.sentinel_id = sentinel_id;
    }

    
}
