package com.bbva.reports.engine.processor;

import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;

import java.util.List;

public interface IProcessor {
    public ReportData getData(String query, List<ReportSourceParam> args);
    public boolean support(ReportSource source);
}
