package com.bbva.reports.engine.implement;

import com.bbva.reports.engine.model.Report;
import com.bbva.reports.engine.model.ReportCollection;

import java.io.File;
import java.io.InputStream;

public class JsonReportCollection implements ReportCollection {

    public static final String EXT_JSON = "json";

    private String path;
    private String extension;

    public JsonReportCollection() {
    }

    public JsonReportCollection(String path) {
        this(path, EXT_JSON);
    }

    public JsonReportCollection(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    public Report openReport(String filName) {
        return null;
    }

    @Override
    public Report openReport(File filName) {
        return null;
    }

    @Override
    public Report openReport(InputStream fileStream) {
        return null;
    }
}
