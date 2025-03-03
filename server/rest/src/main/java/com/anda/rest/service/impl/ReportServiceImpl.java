package com.anda.rest.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
// import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.stereotype.Service;
import com.anda.rest.model.Report;
import com.anda.rest.repository.ReportRepository;
import com.anda.rest.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    // Constructor for ReportServiceImpl
    public ReportServiceImpl(ReportRepository ReportRepository) {
        this.reportRepository = ReportRepository;
    }

    @Override
    public boolean createReport(Report report) {
        try {
            reportRepository.save(report);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Report getReport(int id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getReportByDevice(int device_id) {
        return reportRepository.findReportsByDeviceId(device_id);
    }
}
