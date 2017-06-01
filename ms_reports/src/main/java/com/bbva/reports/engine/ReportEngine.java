package com.bbva.reports.engine;

import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.Report;
import com.bbva.reports.engine.model.ReportCollection;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;
import com.bbva.reports.engine.datasource.IDataSource;
import com.bbva.reports.engine.template.ITemplateEngine;
import com.bbva.reports.engine.visualizer.birt.BirtView;
import com.bbva.reports.engine.visualizer.birt.BirtViewFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportEngine {

    private IDataSource processor;
    private ITemplateEngine template;
    private ReportCollection reportCollection;
    private BirtViewFactory birtViewFactory;

    public ReportEngine(
            IDataSource processor,
            ITemplateEngine templateEngine,
            ReportCollection reportCollection,
            BirtViewFactory birtViewFactory) {
        this.processor = processor;
        this.template = templateEngine;
        this.reportCollection = reportCollection;
        this.birtViewFactory = birtViewFactory;
    }

    public BirtView generateReport(
            int reportId,
            Map<String, Object> inputParams
    ) {
        Report report = reportCollection.findOne(reportId);

        if (null == report) {
            throw new IllegalArgumentException("El Reporte solicitado no existe");
        }

        List<ReportSource> sources = report.sources();

        BirtView birtView = birtViewFactory.createBirtView();

        birtView.setReportType(report.type().name());
        birtView.getReportParameters().putAll(proccessDataSources(sources, inputParams));
        birtView.setReportFileName(report.type() == Report.FormatType.HTML ? "default.rptdesign" : report.content());

        // Procesar un template :)
        if (report.type() != Report.FormatType.XML) {
            birtView.getReportParameters()
                    .put("content", template.renderString(report.content(), birtView.getReportParameters()));
        }

        return birtView;
    }

    private Map<String, Object> proccessDataSources(
            List<ReportSource> sources,
            Map<String, Object> inputParams
    ) {

        Map<String, Object> params = new HashMap<>();

        for (ReportSource source : sources) {

            List<ReportSourceParam> sourceParams = source.params();

            populateParams(sourceParams, inputParams);

            System.out.println("[Source Type] : " + source.type().name());
            System.out.println("[Input Params]: " + inputParams);
            System.out.println("[Source Params]: " + sourceParams.toString());

            if (!this.processor.support(source)) {
                throw new IllegalArgumentException("El tipo de fuente de datos no es correcto");
            }

            ReportData data = this.processor.getData(source.content(), sourceParams);

            System.out.println("Source is Collection  : " + source.isCollection());
            System.out.println("Source object :" + source);
            System.out.println("Source size : " + data.size());
            System.out.println("Params : " + params);
            System.out.println("Params size : " + params.size());

            params.put(source.name(), source.isCollection()  ? data : data.size() > 0 ? data.get(0) : data);
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
}
