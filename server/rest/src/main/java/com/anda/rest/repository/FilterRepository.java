package com.anda.rest.repository;

import com.anda.rest.model.Filter;
import com.anda.rest.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FilterRepository extends JpaRepository <Filter, String> {
    @Query("SELECT user_preferences FROM Filter user_preferences WHERE user_preferences.username = :username")
    List<Filter> findFiltersByUsername(@Param("username") String username);
}
