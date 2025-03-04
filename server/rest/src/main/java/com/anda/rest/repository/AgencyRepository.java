package com.anda.rest.repository;

import com.anda.rest.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for Agency objects
 * @author Gleb Bereziuk (gl3bert)
 */

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

    @Query("SELECT a FROM Agency a WHERE a.agency_id = :agency_id")
    Agency findAgencyByAgency_id(int agency_id);
}
