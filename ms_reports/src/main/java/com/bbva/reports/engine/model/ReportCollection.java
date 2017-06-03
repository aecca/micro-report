package com.bbva.reports.engine.model;

import java.io.IOException;

public interface ReportCollection {
    public Report openReport(String filName) throws IOException;
}
