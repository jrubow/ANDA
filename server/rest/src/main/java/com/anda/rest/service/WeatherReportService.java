package com.anda.rest.service;

import com.anda.rest.model.WeatherReport;

import java.util.List;
import java.util.Map;

public interface WeatherReportService {
    boolean createWeatherReport(WeatherReport weatherReport);
    public String updateWeatherReport(Map<String, Object> updates);
    public String deleteWeatherReport(int report_id);
    public WeatherReport getWeatherReport(int report_id);
    public List<WeatherReport> getAllWeatherReports();
    public List<WeatherReport> getAllWeatherReportsByReport_type(String report_type);
}
