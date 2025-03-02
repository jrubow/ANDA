package com.anda.rest.repository;

import com.anda.rest.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilterRepository extends JpaRepository <Filter, String> {
    Filter findByUsername(String username);
}
