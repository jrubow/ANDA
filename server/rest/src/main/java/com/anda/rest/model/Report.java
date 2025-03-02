package com.anda.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="reports")
public class Report {
    @Id
    private int id;
    private int sensor_id;
    private String report_type;
    private double value;
    private String units;

    public Report() {
    }

    public Report(int id, int sensor_id, String report_type, double value, String units) {
        this.id = id;
        this.sensor_id = sensor_id;
        this.report_type = report_type;
        this.value = value;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int report_id) {
        this.id = report_id;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }



}
