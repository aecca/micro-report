package com.bbva.reports.engine.datasource;

import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSourceParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JSONDataSource implements IDataSource {

    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public ReportData getData(String json, List<ReportSourceParam> args) {
        ReportData items = new ReportData();
        try {
            items.add(mapper.readValue(json, new TypeReference<Map<String, Object>>() {}));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean support(ReportSource source)
    {
        return source.type() == ReportSource.Type.JSON;
    }
}
