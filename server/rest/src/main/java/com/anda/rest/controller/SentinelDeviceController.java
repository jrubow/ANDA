package com.anda.rest.controller;

import com.anda.rest.model.SentinelDevice;
import com.anda.rest.service.SentinelDeviceService;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> initalizeSentinelDevice(@RequestBody SentinelDevice device) {
        boolean isCreated = sentinelDeviceService.createSentinelDevice(device);
        return isCreated ? ResponseEntity.ok("SENTINEL DEVICE REGISTERED") : ResponseEntity.status(400).body("SENTINEL DEVICE ALREADY EXISTS");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSentinelDevice(@RequestBody Map<String, Object> updates) {
        try {
            boolean isUpdated = sentinelDeviceService.updateSentinelDevice(updates);
            return isUpdated ? ResponseEntity.ok("SENTINEL DEVICE UPDATED") : ResponseEntity.status(404).body("SENTINEL DEVICE NOT FOUND");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/claim")
    public ResponseEntity<String> claimSentinelDevice(@RequestBody Map<String, Object> body) {
        try {
            System.out.println(body.get("device_id"));
            System.out.println(body.get("password"));
            System.out.println(body.get("username"));
            boolean isClaimed = sentinelDeviceService.claimSentinelDevice((Integer) body.get("device_id"), (String) body.get("password"), (String) body.get("username"));
            return isClaimed ? ResponseEntity.ok("SENTINEL DEVICE " + body.get("device_id") + " CLAIMED") : ResponseEntity.status(400).body("SENTINEL DEVICE NOT FOUND");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping()
    public List<SentinelDevice> getAllUserDetails(String username) {
        return sentinelDeviceService.getAllSentinelDevices();
    }
}