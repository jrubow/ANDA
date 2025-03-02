package com.anda.rest.service;
import com.anda.rest.model.RelayDevice;
import java.util.List;
import java.util.Map;

/**
 * Interface for Relay Device services
 * @author Josh Rubow (jrubow)
 */

 public interface RelayDeviceService {
    public boolean createRelayDevice(RelayDevice device);
    public String updateRelayDevice(Map<String, Object> updates);
    public String deleteRelayDevice(int Relay_id);
    public RelayDevice getRelayDevice(int Relay_id);
    public List<RelayDevice> getAllRelayDevices();
    // boolean registerRelayDevice(RelayDevice device);
}
