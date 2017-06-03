package com.bbva.reports.engine.implement;

import com.bbva.reports.engine.model.Report;
import com.bbva.reports.engine.model.ReportCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public class JsonReportCollection implements ReportCollection {

    public static final String EXT_JSON = "json";

    private String path;
    private String extension;

    public JsonReportCollection() {}

    public JsonReportCollection(String path) {
        this.path = path;
        this.extension = EXT_JSON;
    }

    public JsonReportCollection(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    public Report openReport(String fileName) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(FIELD, ANY);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        String realFileName = getFileName(fileName);
        URL urlFile = getClass().getResource(realFileName);

        if(urlFile == null) {
            throw new IOException("Unable file location : " + realFileName);
        }

        return mapper.readValue(urlFile, Report.class);
    }

    private String getFileName(String filename) {

        StringBuilder strFileName = new StringBuilder();

        if(path != null)  {
            strFileName.append(path);
        }

        strFileName.append("/");
        strFileName.append(filename);

        if(extension == null ) {
            extension = EXT_JSON;
        }

        strFileName.append(".");
        strFileName.append(extension);

        return strFileName.toString();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
