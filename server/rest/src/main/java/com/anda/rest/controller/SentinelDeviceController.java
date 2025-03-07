package com.anda.rest.controller;

import com.anda.rest.model.Device;
import com.anda.rest.model.SentinelDevice;
import com.anda.rest.service.SentinelDeviceService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
// import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * Sentinel Device API processing
 * @author Josh Rubow (jrubow)
 */

@RestController
@RequestMapping("/api/devices/sentinel")
public class SentinelDeviceController {

    SentinelDeviceService sentinelDeviceService;

    public SentinelDeviceController(SentinelDeviceService sentinelDeviceService) {
        this.sentinelDeviceService = sentinelDeviceService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initalizeSentinelDevice(Authentication authentication, @RequestBody SentinelDevice device) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
        }
        boolean isCreated = sentinelDeviceService.createSentinelDevice(device);
        return isCreated ? ResponseEntity.ok("SENTINEL DEVICE REGISTERED") : ResponseEntity.status(400).body("SENTINEL DEVICE ALREADY EXISTS");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSentinelDevice(Authentication authentication, @RequestBody Map<String, Object> updates) {
        try {
            if (authentication != null && authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
                return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
            }
            boolean isUpdated = sentinelDeviceService.updateSentinelDevice(updates);
            return isUpdated ? ResponseEntity.ok("SENTINEL DEVICE UPDATED") : ResponseEntity.status(404).body("SENTINEL DEVICE NOT FOUND");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/claim")
    public ResponseEntity<String> claimSentinelDevice(@RequestBody Map<String, Object> body) {
        String claimRes = "";
        try {
            claimRes = sentinelDeviceService.claimSentinelDevice((Integer) body.get("device_id"), (String) body.get("password"), (int) body.get("agency_id"));
            System.out.println(claimRes);
            return claimRes.equals("ok") ? ResponseEntity.ok("SENTINEL DEVICE " + body.get("device_id") + " CLAIMED") : ResponseEntity.status(400).body(claimRes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(claimRes);
        }
    }

    @PostMapping("/getbyagencyid")
    public ResponseEntity<List<SentinelDevice>> getSentinelDeviceByAgencyId(@RequestBody Map<String, Object> body) {
        List<SentinelDevice> devices = sentinelDeviceService.findByAgencyId((Integer) body.get("agency_id"));
        
        if (devices == null || devices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(devices);
        }
    }

    @GetMapping()
    public List<SentinelDevice> getAllUserDetails(String username) {
        return sentinelDeviceService.getAllSentinelDevices();
    }
}