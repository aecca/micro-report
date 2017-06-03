package com.bbva.reports.engine;

import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.datasource.IDataSource;
import com.bbva.reports.engine.model.Report;
import com.bbva.reports.engine.model.Report.ReportType;
import com.bbva.reports.engine.model.ReportCollection;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;
import com.bbva.reports.engine.template.ITemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportEngine {

    private IDataSource datasource;
    private ITemplateEngine template;
    private ReportCollection reportCollection;
    private ReportViewFactory reportViewFactory;

    public ReportEngine(
            IDataSource datasource,
            ITemplateEngine templateEngine,
            ReportCollection reportCollection,
            ReportViewFactory birtViewFactory) {
        this.datasource = datasource;
        this.template = templateEngine;
        this.reportCollection = reportCollection;
        this.reportViewFactory = birtViewFactory;
    }

    public ReportView createReportView(String reportName, Map<String, Object> inputParams) throws IOException {

        Report report = reportCollection.openReport(reportName);

        List<ReportSource> sources = report.sources();

        ReportView reportView = reportViewFactory.createView();
        reportView.setReportType(report.type());
        reportView.addParams(proccessDataSources(sources, inputParams));
        reportView.addParams(inputParams);
        reportView.setReportContent(report.type() == ReportType.XML ?
                report.content() :
                template.renderString(report.content(), reportView.params()));

        return reportView;
    }

    private Map<String, Object> proccessDataSources(
            List<ReportSource> sources,
            Map<String, Object> inputParams
    ) {

        Map<String, Object> params = new HashMap<>();

        for (ReportSource source : sources) {

            List<ReportSourceParam> sourceParams = source.params();
            populateParams(sourceParams, inputParams);

            if (!this.datasource.support(source)) {
                throw new IllegalArgumentException("El tipo de fuente de datos no es correcto");
            }

            ReportData data = this.datasource.getData(source.content(), sourceParams);

            System.out.println("[Source Type] : " + source.type().name());
            System.out.println("[Input Params]: " + inputParams);
            System.out.println("[Source Params]: " + sourceParams.toString());
            System.out.println("[Source object] :" + source);
            System.out.println("[Source size] : " + data.size());
            System.out.println("[Params] : " + params);
            System.out.println("[Params size] : " + params.size());

            params.put(source.name(), data);
        }

        return params;
    }

    private void populateParams(List<ReportSourceParam> params,
                                Map<String, Object> inputParam) {

        for (ReportSourceParam pm : params) {
            if (pm.isRequired() && !inputParam.containsKey(pm.name())) {
                throw new IllegalArgumentException("El parametro '" + pm.name() + "' es requerido");
            }
            pm.setValue((String) inputParam.get(pm.name()));
        }
    }

    public ReportCollection getReportCollection() {
        return reportCollection;
    }
}
