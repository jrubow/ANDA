package com.anda.rest.repository;

import com.anda.rest.model.Filter;
import com.anda.rest.model.Report;
import com.anda.rest.model.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WeatherReportRepository extends JpaRepository <WeatherReport, Integer> {
    @Query("SELECT weather_reports FROM WeatherReport weather_reports WHERE weather_reports.report_type = :report_type")
    List<WeatherReport> findWeatherReportsByReport_type(@Param("report_type") String report_type);
}