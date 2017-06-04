package com.bbva.reports.engine.model;

import java.io.IOException;
import java.util.List;

public interface ReportCollection {
    public Report openReport(String fileName) throws IOException;
    public List<Report> listReports();
    public void save(Report report) throws IOException;
    public void deleteReport(String fileName) throws IOException;
}
