package com.anda.rest.repository;

import com.anda.rest.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportRepository extends JpaRepository <Report, Integer> {
    Report findById(int id);
}
