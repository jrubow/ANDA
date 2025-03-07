package com.anda.rest.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
// import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.anda.rest.model.RelayDevice;
import com.anda.rest.repository.RelayDeviceRepository;
import com.anda.rest.service.RelayDeviceService;

@Service
public class RelayDeviceServiceImpl implements RelayDeviceService {
    private final RelayDeviceRepository relayDeviceRepository;

    // Constructor for RelayDeviceServiceImpl
    public RelayDeviceServiceImpl(RelayDeviceRepository relayDeviceRepository) {
        this.relayDeviceRepository = relayDeviceRepository;
    }

    @Override
    public boolean createRelayDevice(RelayDevice device) {
        try {
            relayDeviceRepository.save(device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String updateRelayDevice(Map<String, Object> updates) {
        int id = (Integer) updates.get("device_id");
        if (id < 0) {
            throw new IllegalArgumentException("device_id is required for updating user details.");
        }

        // Find the Devic by id
        RelayDevice device = (RelayDevice) relayDeviceRepository.findById(id).orElse(null);
        if (device == null) {
            return "DEVICE_ID: " + id + " IS NOT FOUND";
        }

        Set<String> allowedFields = Set.of("latitude", "longitude", "battery_life", "is_connected", "last_online", "deployed", "deployed", "deployed_date", "sentinel_connection");

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
                    case "sentinel_connection" -> device.setSentinelConnection((Integer) value);
                    default -> throw new IllegalArgumentException("Invalid field: " + key);
                }
            }
        });

        return "RELAY DEVICE UPDATED";
    }

    @Override
    public String deleteRelayDevice(int id) {
        relayDeviceRepository.deleteById(id);
        return "RELAY DEVICE DELETED FROM DATABASE";
    }

    @Override
    public RelayDevice getRelayDevice(int id) {
        return relayDeviceRepository.findById(id).orElse(null);
    }

    @Override
    public List<RelayDevice> getAllRelayDevices() {
        return relayDeviceRepository.findAll();
    }
}
