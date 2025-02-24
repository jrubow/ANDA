package com.anda.rest.repository;

import com.anda.rest.model.APIKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository for holding API Keys of users, which adds a layer of security for our application and also bridges to our database
 */

@Repository
public interface APIKeyRepository extends JpaRepository<APIKey, String>{
    APIKey findByKey(String key); // uses built-in SpringBoot findBy function and specifies what field/class to look for in the database. In this case, the key field of an APIKey object
}
