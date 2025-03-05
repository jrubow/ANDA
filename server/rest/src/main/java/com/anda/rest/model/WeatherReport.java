package com.anda.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="weather_reports")
public class WeatherReport {
    @Id
    @Column(name="report_id")
    private int report_id;
    private String report_type;
    private String units;
    private String message;
    private String source;
    private double render_long;
    private double render_lat;
    private double render_radius;

    public WeatherReport() {
    }

    public WeatherReport(int report_id, String report_type, double value, String units, String message, String source, double render_long, double render_lat, double render_radius) {
        this.report_id = report_id;
        this.report_type = report_type;
        this.units = units;
        this.message = message;
        this.source = source;
        this.render_long = render_long;
        this.render_lat = render_lat;
        this.render_radius = render_radius;
    }

    public int getReport_Id() {
        return this.report_id;
    }

    public void setId(int report_id) {
        this.report_id = report_id;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public double getRender_radius() {
        return render_radius;
    }

    public void setRender_radius(double render_radius) {

    }

}
