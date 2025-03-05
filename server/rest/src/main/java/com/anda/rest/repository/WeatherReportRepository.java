package com.anda.rest.repository;

import com.anda.rest.model.Report;
import com.anda.rest.model.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WeatherReportRepository extends JpaRepository <WeatherReport, Integer> {

}