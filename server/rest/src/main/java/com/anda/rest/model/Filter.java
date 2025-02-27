package com.anda.rest.model;

import jakarta.persistence.*;

/*
 * Filter class which holds what a user would see from their side from weather type to weather occurrences
 * @author Jinhoo Yoon (juhg9543)
 */


@Entity
@Table(name="filters")
public class Filter {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int filter_id;
    private String filter_name;
    private boolean show_snow;
    private boolean show_fires;
    private boolean show_storms;
    private boolean show_rain;
    private boolean show_thunder;

    @Embedded
    private Coordinates coordinates;
    private double radius;

    private final double DEFAULT_RADIUS = 100000.00; // default is global coverage

    public Filter() {
        this.filter_id = 0;
        this.filter_name = "";
        this.coordinates = new Coordinates();
        this.radius = 0;
    }

    public Filter(int filter_id, String filter_name) { // the default is to show everything
        this.filter_id = filter_id;
        this.filter_name = filter_name;
        this.show_snow = true;
        this.show_fires = true;
        this.show_storms = true;
        this.show_rain = true;
        this.show_thunder = true;
        this.coordinates = new Coordinates();
        this.radius = DEFAULT_RADIUS;
    }

    public Filter(int filter_id, String filter_name, boolean show_fires, boolean show_snow, boolean show_storms, boolean show_rain, boolean show_thunder, Coordinates coordinates, double radius) {
        this.filter_id = filter_id;
        this.filter_name = filter_name;
        this.show_snow = show_snow;
        this.show_fires = show_fires;
        this.show_storms = show_storms;
        this.show_rain = show_rain;
        this.show_thunder = show_thunder;
        this.coordinates = coordinates;
        this.radius = radius;
    }

    public void filter() {

    }

    public double getRadius() {
        return this.radius;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public int getFilter_id() {
        return this.filter_id;
    }

    public String getFilter_name() {
        return this.filter_name;
    }

    public boolean getShow_snow() {
        return this.show_snow;
    }

    public boolean getShow_fires() {
        return this.show_fires;
    }

    public boolean getShow_storms() {
        return this.show_storms;
    }

    public boolean getShow_rain() {
        return this.show_rain;
    }

    public boolean getShow_thunder() {
        return this.show_thunder;
    }

    public void setFilter_id(int filter_id) {
        this.filter_id = filter_id;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }

    public void setShow_snow(boolean show_snow) {
        this.show_snow = show_snow;
    }

    public void setShow_fires(boolean show_fires) {
        this.show_fires = show_fires;
    }

    public void setShow_storms(boolean show_storms) {
        this.show_storms = show_storms;
    }

    public void setShow_rain(boolean show_rain) {
        this.show_rain = show_rain;
    }

    public void setShow_thunder(boolean show_thunder) {
        this.show_thunder = show_thunder;
    }
}
