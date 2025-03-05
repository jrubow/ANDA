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
@RequestMapping("/api/weather_report")
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

//    @GetMapping("/report_id")
//    public ResponseEntity<?> getFilterByUsername(@RequestBody Map<String, Object> body) {
//        try {
//            // Extract deviceId from the request
//            int report_id = (String) body.get("report_id")
//
//                return ResponseEntity.badRequest()
//                        .body("ERROR : username SHOULD NOT BE NEGATIVE");
//            }
//
//            // Retrieve filters using the username
//            List<WeatherReport> reports = filterService.getFiltersByUsername(username);
//
//            // Spring Boot will automatically convert the List<Report> to JSON
//            return ResponseEntity.ok(filters);
//        } catch (Exception e) {
//            // Return a JSON formatted error message in case of exception
//            return ResponseEntity.status(500)
//                    .body("ERROR : COULD NOT RETRIEVE USER'S FILTER");
//        }
//    }

}
