package com.bbva.reports.engine;

import com.bbva.reports.engine.visualizer.birt.BirtEngineFactory;

public class ReportViewFactory {

    public ReportView createView() {
        ReportView reportView = new ReportView();
        reportView.setEngine(new BirtEngineFactory().createEngine());

        return reportView;
    }
}
