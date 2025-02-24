package com.anda.rest.model;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Embedded;
import jakarta.persistence.Table;

/*
 * Weather class holds weather information such as location, time, temperature, if it is a fire, ice, rain, hail, or some other event, etc.
 * @Author Jinhoo Yoon (juhg9543)
 */

@Entity
@Table(name="filters")
public class WeatherEvent {
    @Id
    private static int weather_event_id;
    private double temperature;
    private double humidity;
    private double pressure;
    private ZonedDateTime time_reported;
    private double radius;

    @Embedded
    private Coordinates coordinates;

    public WeatherEvent() {
        this.coordinates = new Coordinates(); // set to  dummy values if empty
    }

    public WeatherEvent(double temperature, double humidity, double pressure, double radius, Coordinates coordinates) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.time_reported = ZonedDateTime.now();
        this.radius = radius;
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public double getHumidity() {
        return this.humidity;
    }

    public double getPressure() {
        return this.pressure;
    }

    public ZonedDateTime getTime() {
        return this.time_reported;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
