package com.bbva.reports.engine.implement;

import com.bbva.reports.engine.model.Report;
import com.bbva.reports.engine.model.ReportCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public class JsonReportCollection implements ReportCollection {

    private String path;
    private ObjectMapper mapper;
    private Map<String, String> registry;

    public JsonReportCollection() {
        mapper = new ObjectMapper();
        mapper.setVisibility(FIELD, ANY);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        File file = new File(System.getProperty("user.home"), ".reports");
        path = file.getAbsolutePath();

        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new IllegalArgumentException("Unable create report file in Home directory. Check permissions!");
            }

            File fileRegistry = new File(path + "/registry.map");

            if(file.exists()) {
                try {
                    registry = mapper.readValue(fileRegistry, Map.class);
                } catch (IOException ignored) {}
            }
        }

    }

    @Override
    public Report openReport(String fileName) throws IOException {

        File reportFile = new File(getFileName(fileName));

        if (!reportFile.exists()) {
            throw new IOException("Unable file location : " + fileName);
        }

        return mapper.readValue(reportFile, Report.class);
    }

    @Override
    public List<Report> listReports() {

        File[] listOfFiles = new File(path).listFiles();
        List<Report> reports = new ArrayList<>();

        if (listOfFiles == null) {
            return reports;
        }

        for (File file : listOfFiles) {
            try {
                reports.add(mapper.readValue(file, Report.class));
            } catch (IOException ignored) {}
        }

        return reports;
    }

    @Override
    public void save(Report report) throws IOException {
        mapper.writeValue(new File(getFileName(report.name())), report);
    }

    @Override
    public void deleteReport(String fileName) throws IOException {

        File reportFile = new File(getFileName(fileName));

        if (!reportFile.exists()) {
            throw new IOException("Unnable locate reportfile  : " + fileName);
        }

        if (!new File(reportFile.getPath()).delete()) {
            throw new IOException("Unnable delete report");
        }
    }

    private String getFileName(String filename) {
        return path + "/" + filename + ".json";
    }
}
