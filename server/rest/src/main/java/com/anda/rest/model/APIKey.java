package com.anda.rest.model;

import java.util.UUID;
import java.util.*;

/**
 * APIKey class to instantiate keys for users 
 * @author Jinhoo (juhg9543)
 */
public class APIKey {

    private static Map<String, String>APIKeys = new HashMap<>(); // only temporary, will eventually have to add path to SQL server for accessing actual API keys

    // Generate a unique Key using UUID
    public String generateApiKey() {
        String apiKey = UUID.randomUUID().toString();
        apiKeyStorage.put(apiKey, "guest");  // Store with a guest identifier so we can reuse (placeholder until more functionality is achieved)
        return apiKey;
    }

    public boolean isValidApiKey(String apiKey) {
        return apiKeyStorage.containsKey(apiKey);
    }
}
