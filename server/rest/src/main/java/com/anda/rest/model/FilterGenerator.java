package com.anda.rest.model;

import com.anda.rest.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterGenerator {
    @Autowired
    private FilterRepository filterRepository;

    public synchronized void saveFilter(Filter filter) {
        filterRepository.save(filter);
    }
}
