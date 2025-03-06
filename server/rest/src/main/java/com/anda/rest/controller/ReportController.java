package com.anda.rest.controller;

import com.anda.rest.model.Report;
import com.anda.rest.service.ReportService;

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
@RequestMapping("/api/reports")
public class ReportController {

    ReportService reportService;

    

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> initalizeReport(Authentication authentication, @RequestBody Report report) {
        System.out.println(report.getReportType());
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            boolean isCreated = reportService.createReport(report);
            return isCreated ? ResponseEntity.ok("REPORT CREATED") : ResponseEntity.status(400).body("REPORT ALREADY EXISTS");
        }
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
        }
        return ResponseEntity.status(403).body("Access Denied");
//        boolean isCreated = reportService.createReport(report);
//        return isCreated ? ResponseEntity.ok("REPORT CREATED") : ResponseEntity.status(400).body("REPORT ALREADY EXISTS");
    }

    @GetMapping("/device")
    public ResponseEntity<?> getReportByDevice(Authentication authentication, @RequestBody Map<String, Object> body) {
        try {
            // Extract deviceId from the request body
            if (authentication != null && authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
                return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
            }
            Integer deviceId = (Integer) body.get("deviceId");
            if (deviceId == null) {
                return ResponseEntity.badRequest()
                    .body("ERROR : device_id SHOULD NOT BE NULL");
            }

            // Retrieve reports using the deviceId
            List<Report> reports = reportService.getReportByDevice(deviceId);

            // Spring Boot will automatically convert the List<Report> to JSON
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            // Return a JSON formatted error message in case of exception
            return ResponseEntity.status(500)
                .body("ERROR : COULD NOT RETRIEVE DEVICE");
        }
    }
    
}