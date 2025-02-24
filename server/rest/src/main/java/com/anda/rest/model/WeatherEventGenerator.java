package com.anda.rest.model;

import com.anda.rest.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherEventGenerator {
    @Autowired
    private WeatherRepository weatherRepository;

    public synchronized void saveWeatherEvent(WeatherEvent weatherEvent) {
        weatherRepository.save(weatherEvent);
    }
}
