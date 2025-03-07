package com.anda.rest.service;
import com.anda.rest.model.Report;
import java.util.List;
import java.util.Map;

/**
 * Interface for Report Service
 * @author Josh Rubow (jrubow)
 */

 public interface ReportService {
    public boolean createReport(Report report);
    public Report getReport(int id);
    public List<Report> getAllReports();
    public List<Report> getReportByDevice(int deviceId);
    public void createBatchReport(List<Report> reports);
}
