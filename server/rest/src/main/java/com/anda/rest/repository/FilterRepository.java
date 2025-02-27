package com.anda.rest.repository;

import com.anda.rest.model.APIKey;
import com.anda.rest.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository for holding filters for users to use and customize as desired
 */

@Repository
public interface FilterRepository extends JpaRepository<Filter, Integer>{
    Filter findByFilter_id(int filter_id); // uses built-in SpringBoot findBy function and specifies what field/class to look for in the database. In this case, the key field of an APIKey object
}
