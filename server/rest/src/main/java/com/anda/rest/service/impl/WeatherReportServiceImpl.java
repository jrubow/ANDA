package com.anda.rest.service.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;
// import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anda.rest.model.WeatherReport;
import com.anda.rest.repository.WeatherReportRepository;
import com.anda.rest.service.WeatherReportService;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private final WeatherReportRepository weatherReportRepository;

    // Constructor for RelayDeviceServiceImpl
    public WeatherReportServiceImpl(WeatherReportRepository weatherReportRepository) {
        this.weatherReportRepository = weatherReportRepository;
    }

    @Override
    public boolean createWeatherReport(WeatherReport weatherReport) {
        try {
            weatherReportRepository.save(weatherReport);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String updateWeatherReport(Map<String, Object> updates) {
        int report_id = (Integer) updates.get("report_id");
        if (report_id < 0) {
            throw new IllegalArgumentException("report_id must be non-negative");
        }

        // Find the Devic by id
        WeatherReport weatherReport = (WeatherReport) weatherReportRepository.findById(report_id).orElse(null);
        if (weatherReport == null) {
            return "USERNAME: " + report_id + " IS NOT FOUND";
        }

        Set<String> allowedFields = Set.of("report_type", "units", "message", "source", "render_long", "render_lat", "render_rad");

        for (String key : updates.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException("Field '" + key + "' cannot be modified.");
            }
        }

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "report_type" -> weatherReport.setReport_type((String) value);
                    case "units" -> weatherReport.setUnits((String) value);
		            case "message" -> weatherReport.setMessage((String) value);
		            case "source" -> weatherReport.setSource((String) value);
                    case "render_long" -> weatherReport.setRender_long((Double) value);
                    case "render_lat" -> weatherReport.setRender_lat((Double) value);
                    case "render_rad" -> weatherReport.setRender_radius((Double) value);
                    default -> throw new IllegalArgumentException("Invalid field: " + key);
                }
            }
        });

        return "WEATHER REPORT UPDATED";
    }

    @Override
    public String deleteWeatherReport(int report_id) {
        weatherReportRepository.deleteById(report_id);
        return "WEATHER REPORT DELETED FROM DATABASE";
    }

    @Override
    public WeatherReport getWeatherReport(int report_id) {
        return weatherReportRepository.findById(report_id).orElse(null);
    }

    @Override
    public List<WeatherReport> getAllWeatherReports() {
        return weatherReportRepository.findAll();
    }
}
