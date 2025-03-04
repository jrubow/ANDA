package com.anda.rest.service.impl;

import com.anda.rest.model.Agency;
import com.anda.rest.repository.AgencyRepository;
import com.anda.rest.service.AgencyService;
import org.springframework.stereotype.Service;

/**
 * Implementation for Agency service
 * @author Gleb Bereziuk (gl3bert)
 */

@Service
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;
    public AgencyServiceImpl(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @Override
    public boolean existsById(int agency_id) {
        return agencyRepository.findAgencyByAgency_id(agency_id) != null;
    }

    @Override
    public Agency getAgency(int agency_id) {
        return agencyRepository.findAgencyByAgency_id(agency_id);
    }
}
