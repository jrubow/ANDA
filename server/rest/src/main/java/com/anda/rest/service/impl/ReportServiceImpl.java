package com.anda.rest.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import com.anda.rest.model.Report;
import com.anda.rest.repository.ReportRepository;
import com.anda.rest.service.ReportService;
import com.anda.rest.service.SentinelDeviceService;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final SentinelDeviceService sentinelDeviceService;

    // Inject both ReportRepository and SentinelDeviceService
    public ReportServiceImpl(ReportRepository reportRepository, SentinelDeviceService sentinelDeviceService) {
        this.reportRepository = reportRepository;
        this.sentinelDeviceService = sentinelDeviceService;
    }

    @Override
    public boolean createReport(Report report) {
        try {
            report.setReportId(getNextReportId());
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

    @Override
    public void createBatchReport(List<Report> reports) {
        for (Report report : reports) {
            createReport(report);
        }

        if (!reports.isEmpty()) {
            System.out.println(reports.get(0));
            System.out.println(reports.get(0).getDeviceId());
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("device_id", reports.get(0).getDeviceId());
            updateData.put("last_online", reports.get(0).getTimestamp());
            sentinelDeviceService.updateSentinelDevice(updateData);
        }
    }

    public int getNextReportId() {
        Integer maxId = reportRepository.findMaxReportId();
        return (maxId == null) ? 1 : maxId + 1;
    }
}
