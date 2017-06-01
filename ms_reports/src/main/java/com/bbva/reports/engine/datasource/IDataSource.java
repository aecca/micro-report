package com.bbva.reports.engine.datasource;

import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;

import java.util.List;

public interface IDataSource {
    public ReportData getData(String query, List<ReportSourceParam> args);
    public boolean support(ReportSource source);
}
