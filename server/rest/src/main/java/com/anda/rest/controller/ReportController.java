package com.anda.rest.controller;

import com.anda.rest.model.Report;
import com.anda.rest.service.ReportService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
// import java.security.SecureRandom;
import java.util.List;
// import java.util.Map;
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
    public ResponseEntity<String> initalizeReport(@RequestBody Report report) {
        System.out.println(report.getReportType());
        boolean isCreated = reportService.createReport(report);
        return isCreated ? ResponseEntity.ok("REPORT CREATED") : ResponseEntity.status(400).body("REPORT ALREADY EXISTS");
    }

    @GetMapping("/device")
    public ResponseEntity<?> getReportByDevice(@RequestBody Map<String, Object> body) {
        try {
            // Extract deviceId from the request body
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