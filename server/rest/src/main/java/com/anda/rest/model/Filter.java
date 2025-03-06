package com.anda.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="user_preferences")
public class Filter {
    @Id
    private String username;
    private boolean ice;
    private boolean flood;
    private double render_long;
    private double render_lat;
    private double render_rad;

    public Filter() {
    }

    public Filter(String username, boolean ice, boolean flood, double render_long, double render_lat, float render_rad) {
        this.username = username;
        this.ice = ice;
        this.flood = flood;
        this.render_long = render_long;
        this.render_lat = render_lat;
        this.render_rad = render_rad;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public boolean isIce() {
        return ice;
    }
    public void setIce(boolean ice) {
        this.ice = ice;
    }
    public boolean isFlood() {
        return flood;
    }
    public void setFlood(boolean flood) {
        this.flood = flood;
    }
    public double getRender_long() {
        return render_long;
    }
    public void setRender_long(double render_long) {
        this.render_long = render_long;
    }
    public double getRender_lat() {
        return render_lat;
    }
    public void setRender_lat(double render_lat) {
        this.render_lat = render_lat;
    }
    public double getRender_rad() {
        return render_rad;
    }
    public void setRender_rad(double render_rad) {
        this.render_rad = render_rad;
    }
}

