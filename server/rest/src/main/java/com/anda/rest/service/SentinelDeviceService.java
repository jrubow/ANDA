package com.anda.rest.service;
import com.anda.rest.model.SentinelDevice;
import java.util.List;

/**
 * Interface for Sentinel Device services
 * @author Josh Rubow (jrubow)
 */

 public interface SentinelDeviceService {
    public String createSentinelDevice(SentinelDevice device);
    public String updateSentinelDevice(SentinelDevice device);
    public String deleteSentinelDevice(int sentinel_id);
    public SentinelDevice getSentinelDevice(int sentinel_id);
    public List<SentinelDevice> getAllSentinelDevices();
    boolean registerSentinelDevice(SentinelDevice device);
}
