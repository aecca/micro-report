package com.bbva.reports.engine.model;

import java.io.File;
import java.io.InputStream;

public interface ReportCollection {
    public Report openReport(String filName);
    public Report openReport(File filName);
    public Report openReport(InputStream fileStream);
}
