package com.anda.rest.controller;

import com.anda.rest.model.Filter;
import com.anda.rest.model.WeatherReport;
import com.anda.rest.service.WeatherReportService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
// import java.security.SecureRandom;
import java.util.List;
// import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * Report API processing
 * @author Josh Rubow (jrubow)
 */

@RestController
@RequestMapping("/api/weatherReport")
public class WeatherReportController {

    WeatherReportService weatherReportService;



    public WeatherReportController(WeatherReportService 
		    weatherReportService) {
        this.weatherReportService = weatherReportService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWeatherReportAccess(Authentication authentication, @RequestBody WeatherReport weatherReport) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            boolean isCreated = weatherReportService.createWeatherReport(weatherReport);
            return isCreated ? ResponseEntity.ok("REPORT CREATED") : ResponseEntity.status(400).body("FILTER ALREADY EXISTS");
        }
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllWeatherReports() {
        try {
            List<WeatherReport> reports = weatherReportService.getAllWeatherReports();
            if (reports.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("ERROR: COULD NOT RETRIEVE REPORTS");
        }
    }


    @GetMapping("/report/{report_id}")
    public ResponseEntity<?> getFilterByReportId(@PathVariable("report_id") int reportId) {
        try {
            if (reportId < 0) {
                return ResponseEntity.badRequest()
                    .body("ERROR: report_id SHOULD NOT BE NEGATIVE");
            }

            // Retrieve reports using the reportId
            List<WeatherReport> reports = filterService.getFiltersByReportId(reportId);

            // Return the reports
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("ERROR: COULD NOT RETRIEVE REPORTS");
        }
    }

}
