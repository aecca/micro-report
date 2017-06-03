package com.bbva.reports.engine;

import com.bbva.reports.engine.model.Report.ReportType;
import com.bbva.reports.engine.visualizer.birt.BirtEngineFactory;
import com.bbva.reports.engine.visualizer.birt.BirtTemplate;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ReportView extends AbstractView {

    private static final String PARAM_FORMAT  = "format";
    private static final String FORMAT_HTML   = "html";
    private static final String FORMAT_PDF    = "pdf";
    private static final String PARAM_CONTENT = "content";

    private IReportEngine engine;

    private Map<String, Object> params;
    private ReportType reportType;
    private String reportContent;


    public ReportView() {
        this(new BirtEngineFactory(), new HashMap<>());
    }

    public ReportView(BirtEngineFactory engineFactory) {
        this(engineFactory, new HashMap<>());
    }

    public ReportView(
            BirtEngineFactory engineFactory,
            Map<String, Object> params) {

        this.params = params;
        this.engine = engineFactory.createEngine();
    }

    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        IReportRunnable runnable;
        IRenderOption options = new RenderOption();

        if(reportType == ReportType.HTML) {
            params.put(PARAM_CONTENT, reportContent);
            reportContent = BirtTemplate.HTML_TEMPLATE;
        }

        runnable = engine.openReportDesign(getStreamContent(reportContent));
        IRunAndRenderTask runAndRenderTask = engine.createRunAndRenderTask(runnable);
        runAndRenderTask.setParameterValues(params);

        String format = request.getParameter(PARAM_FORMAT);

        if (format == null) {
            format = FORMAT_HTML;
        }

        switch (format.toLowerCase()) {
            case FORMAT_HTML:
                HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
                htmlOptions.setOutputFormat("html");
                htmlOptions.setOutputStream(response.getOutputStream());
                htmlOptions.setImageHandler(new HTMLServerImageHandler());
                runAndRenderTask.setRenderOption(htmlOptions);
                break;

            case FORMAT_PDF:
                PDFRenderOption pdfOptions = new PDFRenderOption(options);
                pdfOptions.setOutputFormat("pdf");
                pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
                pdfOptions.setOutputStream(response.getOutputStream());
                runAndRenderTask.setRenderOption(pdfOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported report format type : " + format);
        }

        response.setContentType(engine.getMIMEType(format));

        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, request);
        runAndRenderTask.run();
        runAndRenderTask.close();
    }

    private InputStream getStreamContent(String content) {
        return new ByteArrayInputStream(reportContent.getBytes(StandardCharsets.UTF_8));
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public void addParams(Map<String, Object> params) {
        this.params.putAll(params);
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String name, Object value) {
        this.params.put(name, value);
    }

    public Map<String, Object> params() {
        return params;
    }

    public void setEngine(IReportEngine engine) {
        this.engine = engine;
    }

    public void destroy() {
        this.engine.destroy();;
    }
}
