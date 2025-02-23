package com.anda.rest.model;

import java.util.UUID;
import java.util.*;

/**
 * APIKey class to instantiate keys for users 
 * @author Jinhoo (juhg9543)
 */
public class APIKey {

    private static Map<String, String>APIKeys = new HashMap<>(); // only temporary, will eventually have to add path to SQL server for accessing actual API keys
    private String APIKey;
    // Generate a unique Key using UUID
    public String generateApiKey() {
        APIKey = UUID.randomUUID().toString();
        APIKeys.put(apiKey, "guest");  // Store with a guest identifier so we can reuse (placeholder until more functionality is achieved)
        return APIKey;
    }

    public boolean isValidApiKey(String APIKey) {
        return apiKeyStorage.containsKey(APIKey);
    }
}
