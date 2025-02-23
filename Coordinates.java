package com.anda.rest.model;

import jakarta.persistence.Embeddable;

/**
 * Coordinates class to be used for all geographical information
 * @author Jinhoo (juhg9543)
 */

@Embeddable
public class Coordinates {
    private double longitude;
    private double latitude;

    private final double nonValidLatitude = Double.NaN;
    private final double nonValidLongitude = Double.NaN;

    public Coordinates() { //  for instantiation purposes
        this.longitude = nonValidLongitude;
        this.latitude = nonValidLatitude;
    }

    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setCoordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
