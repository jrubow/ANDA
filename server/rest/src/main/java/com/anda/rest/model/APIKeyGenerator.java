package com.anda.rest.model;

import java.util.UUID;
import java.util.*;

public class APIKeyGenerator {

    private static Map<String, String>APIKeys = new HashMap<>();

    // Generate a random API Key (for simplicity, using UUID)
    public String generateApiKey() {
        String apiKey = UUID.randomUUID().toString();
        APIKeys.put(apiKey, "guest");  // Store with a guest identifier
        return apiKey;
    }

    // Verify if the API Key is valid
    public boolean isValidApiKey(String APIKey) {
        return APIKeys.containsKey(APIKey);
    }
}
