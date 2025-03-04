package com.anda.rest.repository;

import com.anda.rest.model.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeatherReportRepository extends JpaRepository <WeatherReport, Integer> {
    WeatherReport findById(int report_id);
}