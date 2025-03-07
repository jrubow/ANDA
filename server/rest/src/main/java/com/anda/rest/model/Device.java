package com.anda.rest.model;

import java.time.LocalDateTime;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

/**
 * Abstract class for devices
 * @author Josh Rubow (jrubow)
 */

@MappedSuperclass
public abstract class Device {

    @Id
    @Column(nullable = false, unique = true)
    private int device_id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column( nullable = false)
    private double battery_life;

    // @Column(nullable = false)
    private String last_online;

    // @Column(nullable = false)
    private LocalDateTime deployed_date;

    @Column(nullable = false)
    private int deployed;

    @Column(nullable = false)
    private int is_connected;

    // Default constructor
    public Device() {}

    // Parameterized constructor
    public Device(int device_id, double latitude, double longitude, double battery_life,
                      String last_online, LocalDateTime deployed_date, int deployed) {
        this.device_id = device_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery_life = battery_life;
        this.last_online = last_online;
        this.deployed_date = deployed_date;
        this.deployed = deployed;
    }

    // Getters and Setters
    public int getDeviceId() {
        return device_id;
    }

    public void setDeviceId(int device_id) {
        this.device_id = device_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getBatteryLife() {
        return battery_life;
    }

    public void setBatteryLife(double battery_life) {
        this.battery_life = battery_life;
    }

    public String getLastOnline() {
        return last_online;
    }

    public void setLastOnline(String last_online) {
        this.last_online = last_online;
    }

    public LocalDateTime getDeployedDate() {
        return deployed_date;
    }

    public void setDeployedDate(LocalDateTime deployed_date) {
        this.deployed_date = deployed_date;
    }

    public int getDeployed() {
        return deployed;
    }

    public void setDeployed(int deployed) {
        this.deployed = deployed;
    }

    public int getIsConnected() {
        return this.is_connected;
    }

    public void setIsConnected(int is_connected) {
        if (is_connected == 1 || is_connected == 0) {
            this.is_connected = is_connected;
        } else {
            throw new IllegalArgumentException("Parameter must be 0 or 1");
        }
    }

}
