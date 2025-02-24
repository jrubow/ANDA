package com.anda.rest.model;

import com.anda.rest.repository.APIKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
 * Class APIKeyGenerator that creates API keys for users and generators alike
 * @author Jinhoo Yoon (juhg9543)
 */

@Service
public class APIKeyGenerator {

    @Autowired
    APIKeyRepository apiKeyRepository; // makes sure that all keys are automatically stored in the database

    // private static AtomicInteger guest_id = new AtomicInteger(); // allows for safe updating of guest_id with threading

    public synchronized void saveGuestKey(APIKey key) {
        apiKeyRepository.save(key);  // put in hashmap, but each guest has their own unique APIKey so you increment the Atomic Integer at each step
    }

    public synchronized void saveUserKey(APIKey key) {
        apiKeyRepository.save(key);
    }

}
