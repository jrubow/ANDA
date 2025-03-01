package com.anda.rest.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Class for Sentinel Devices
 * @author Josh Rubow (jrubow)
 */

@Entity
@Table(name = "sentinel_device")
public class SentinelDevice extends Device {

    // Additional fields specific to SentinelDevice
    private int is_connected;
    private int num_connected_devices;

    // Default constructor
    public SentinelDevice() {}

    // Parameterized constructor
    public SentinelDevice(int sensor_id, double latitude, double longitude, double battery_life,
                          LocalDateTime last_online, LocalDateTime deployed_date, int deployed,
                          int is_connected, int num_connected_devices) {
        super(sensor_id, latitude, longitude, battery_life, last_online, deployed_date, deployed);
        this.is_connected = is_connected;
        this.num_connected_devices = num_connected_devices;
    }

    // Getters and Setters for additional fields
    public int getIs_connected() {
        return is_connected;
    }

    public void setIs_connected(int is_connected) {
        this.is_connected = is_connected;
    }

    public int getNum_connected_devices() {
        return num_connected_devices;
    }

    public void setNum_connected_devices(int num_connected_devices) {
        this.num_connected_devices = num_connected_devices;
    }
}
