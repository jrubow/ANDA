package com.anda.rest.service;
import com.anda.rest.model.SentinelDevice;
import java.util.List;
import java.util.Map;

/**
 * Interface for Sentinel Device services
 * @author Josh Rubow (jrubow)
 */

 public interface SentinelDeviceService {
    public boolean createSentinelDevice(SentinelDevice device);
    public boolean updateSentinelDevice(Map<String, Object> updates);
    public String deleteSentinelDevice(int id);
    public SentinelDevice getSentinelDevice(int id);
    public List<SentinelDevice> getAllSentinelDevices();
    public boolean claimSentinelDevice(int id, String password, String username);
}
