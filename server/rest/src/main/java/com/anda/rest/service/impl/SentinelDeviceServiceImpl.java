package com.anda.rest.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
// import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.stereotype.Service;
import com.anda.rest.model.SentinelDevice;
import com.anda.rest.repository.SentinelDeviceRepository;
import com.anda.rest.service.SentinelDeviceService;

@Service
public class SentinelDeviceServiceImpl implements SentinelDeviceService {
    private final SentinelDeviceRepository sentinelDeviceRepository;

    // Constructor for SentinelDeviceServiceImpl
    public SentinelDeviceServiceImpl(SentinelDeviceRepository sentinelDeviceRepository) {
        this.sentinelDeviceRepository = sentinelDeviceRepository;
    }

    @Override
    public int createSentinelDevice(SentinelDevice device) {
        try {
            sentinelDeviceRepository.save(device);
            return sentinelDeviceRepository.findMaxDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public boolean updateSentinelDevice(Map<String, Object> updates) {
        int id = (Integer) updates.get("device_id");
        System.out.println(id);
        if (id < 0) {
            throw new IllegalArgumentException("device_id is required for updating user details.");
        }

        // Find the Device by id
        SentinelDevice device = (SentinelDevice) sentinelDeviceRepository.findById(id).orElse(null);
        if (device == null) {
            return false; // Device not found
        }


        updates.remove("device_id");
        Set<String> allowedFields = Set.of("latitude", "longitude", "battery_life", "last_online", "deployed",  "deployed_date", "password", "num_connected_devices");

        for (String key : updates.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException("Field '" + key + "' cannot be modified.");
            }
        }

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "latitude" -> device.setLatitude((Double) value);
                    case "longitude" -> device.setLongitude((Double) value);
                    case "battery_life" -> device.setBatteryLife((Integer) value);
                    case "is_connected" -> device.setIsConnected((Integer) value);
                    case "last_online" -> device.setLastOnline((String) value);
                    case "deployed" -> device.setDeployed((Integer) value);
                    case "deployed_date" -> device.setDeployedDate((LocalDateTime) value);
                    case "num_connected_devices" -> device.setNumConnectedDevices((Integer) value);
                    case "password" -> device.setPassword((String) value);
                    default -> throw new IllegalArgumentException("Invalid field: " + key);
                }
            }
        });

        // Save updated device to the repository
        try {
            sentinelDeviceRepository.save(device);
            return true; // Successfully updated
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return false; // Failed to update
        }
    }

    @Override
    public String deleteSentinelDevice(int id) {
        sentinelDeviceRepository.deleteById(id);
        return "SENTINEL DEVICE DELETED FROM DATABASE";
    }

    @Override
    public SentinelDevice getSentinelDevice(int id) {
        return sentinelDeviceRepository.findById(id).orElse(null);
    }

    @Override
    public List<SentinelDevice> getAllSentinelDevices() {
        return sentinelDeviceRepository.findAll();
    }

    @Override
    public String claimSentinelDevice(int id, String password, int agency_id) {
        System.out.println(agency_id);
        SentinelDevice device = sentinelDeviceRepository.findById(id).orElse(null);
        if (device == null || device.getPassword() == null) {
            return "DEVICE DOES NOT EXIST";
        } else if (device.getAgencyId() != 0) {
            return "DEVICE IS ALREADY REGISTERED";
        } else if (!device.getPassword().equals(password)) {
            return "INCORRECT DEVICE PASSWORD";
        }

        device.setAgencyId(agency_id);
        try {
            sentinelDeviceRepository.save(device);
            return "ok " + device.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "INTERNAL SERVER ERROR";
        }
    }

    @Override
    public List<SentinelDevice> findByAgencyId(int agencyId) {
        return sentinelDeviceRepository.findByAgencyId(agencyId);
    }
}
