package com.anda.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Report class for the reports table
 * @author Josh Rubow (jrubow)
 */

@Entity
@Table(name = "reports")
public class Report {
    
    @Id
    @Column(name = "report_id")
    private int report_id;
    
    @Column(name = "device_id")
    private int device_id;
    
    @Column(name = "report_type")
    private String report_type;
    
    @Column(name = "value")
    private double value;
    
    @Column(name = "units")
    private String units;

    public Report() {
    }

    @JsonCreator
    public Report(
            @JsonProperty("device_id") int device_id,
            @JsonProperty("report_type") String report_type,
            @JsonProperty("value") double value,
            @JsonProperty("units") String units) {
        this.device_id = device_id;
        this.report_type = report_type;
        this.value = value;
        this.units = units;
    }

//    public Report(int device_id, String report_type, double value, String units) {
//        this.device_id = device_id;
//        this.report_type = report_type;
//        this.value = value;
//        this.units = units;
//    }

    // Getters and Setters
    public int getReportId() {
        return report_id;
    }

    public int getDeviceId() {
        return device_id;
    }

    public String getReportType() {
        return report_type;
    }

    public double getValue() {
        return value;
    }

    public String getUnits() {
        return units;
    }

    public void setReportId(int report_id) {
        this.report_id = report_id;
    }

    public void setDeviceId(int device_id) {
        this.device_id = device_id;
    }

    public void setReportType(String report_type) {
        this.report_type = report_type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}