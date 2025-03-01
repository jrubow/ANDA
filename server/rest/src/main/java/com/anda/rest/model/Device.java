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

    @Column(nullable = false)
    private LocalDateTime last_online;

    @Column(nullable = false)
    private LocalDateTime deployed_date;

    @Column(nullable = false)
    private int deployed;

    // Default constructor
    public Device() {}

    // Parameterized constructor
    public Device(int device_id, double latitude, double longitude, double battery_life,
                      LocalDateTime last_online, LocalDateTime deployed_date, int deployed) {
        this.device_id = device_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery_life = battery_life;
        this.last_online = last_online;
        this.deployed_date = deployed_date;
        this.deployed = deployed;
    }

    // Getters and Setters
    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
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

    public double getBattery_life() {
        return battery_life;
    }

    public void setBattery_life(double battery_life) {
        this.battery_life = battery_life;
    }

    public LocalDateTime getLast_online() {
        return last_online;
    }

    public void setLast_online(LocalDateTime last_online) {
        this.last_online = last_online;
    }

    public LocalDateTime getDeployed_date() {
        return deployed_date;
    }

    public void setDeployed_date(LocalDateTime deployed_date) {
        this.deployed_date = deployed_date;
    }

    public int getDeployed() {
        return deployed;
    }

    public void setDeployed(int deployed) {
        this.deployed = deployed;
    }
}
