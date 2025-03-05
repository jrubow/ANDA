package com.anda.rest.service;

import com.anda.rest.model.Agency;

/**
 * Interface for Agency service
 * @author Gleb Bereziuk (gl3bert)
 */

public interface AgencyService {
    public boolean existsById(int agency_id);
    public Agency getAgency(int agency_id);
}
