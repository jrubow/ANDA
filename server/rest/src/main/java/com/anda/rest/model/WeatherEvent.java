package com.anda.rest.model;

import java.time.ZonedDateTime;

import jakarta.persistence.*;

/*
 * Weather class holds weather information such as location, time, temperature, if it is a fire, ice, rain, hail, or some other event, etc.
 * @Author Jinhoo Yoon (juhg9543)
 */

@Entity
@Table(name="weather_events")
public class WeatherEvent {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int weather_event_id;
    private String type;
    private ZonedDateTime time_reported;
    private double radius;

    @Embedded
    private Coordinates coordinates;

    public WeatherEvent() {
        this.coordinates = new Coordinates(); // set to  dummy values if empty
        this.type = "";
    }

    public WeatherEvent(String type, double radius, Coordinates coordinates) throws WeatherException {
        if (type.equals("snow") || type.equals("rain") || type.equals("fire") || type.equals("thunder") || type.equals("heat_wave")) {

        } else {
            throw new WeatherException("Invalid weather event type");
        }
        this.time_reported = ZonedDateTime.now();
        this.radius = radius;
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public ZonedDateTime getTime() {
        return this.time_reported;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
