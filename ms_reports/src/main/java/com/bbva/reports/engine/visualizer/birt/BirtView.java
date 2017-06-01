package com.bbva.reports.engine.visualizer.birt;

import org.eclipse.birt.report.engine.api.*;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BirtView extends AbstractView {

    private static final String PARAM_ISNULL = "__isnull";

    private IReportEngine birtEngine;
    private String reportFormatRequestParameter = "format";
    private IRenderOption renderOptions;
    private String reportsRootPath;
    private String reportFileName;
    private Map<String, Object> reportParameters;
    private String reportType;

    public BirtView() {
        this.reportParameters = new HashMap<>();
    }

    public Map<String, Object> getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(Map<String, Object> reportParameters) {
        this.reportParameters = reportParameters;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public void setRenderOptions(IRenderOption ro) {
        this.renderOptions = ro;
    }

    public String getReportsRootPath() {
        return reportsRootPath;
    }

    public void setReportsRootPath(String reportsRootPath) {
        this.reportsRootPath = reportsRootPath;
    }

    public void setReportFormatRequestParameter(String rf) {
        Assert.hasText(rf, "the report format parameter must not be null");
        this.reportFormatRequestParameter = rf;
    }

    protected void renderMergedOutputModel(
            Map map, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ServletContext sc = request.getSession().getServletContext();
        IReportRunnable runnable;

        if(reportType.equals("HTML")) {
            URL filePath = this.getClass().getResource(getReportsRootPath() + "/" + reportFileName);
            runnable = birtEngine.openReportDesign(getPath(filePath));
        }else {
            InputStream stream = new ByteArrayInputStream(reportFileName.getBytes(StandardCharsets.UTF_8));
            runnable = birtEngine.openReportDesign(stream);
        }
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(runnable);

        Map<String, Object> rparam = discoverAndSetParameters(runnable, request);
        rparam.putAll(this.getReportParameters());
        runAndRenderTask.setParameterValues(rparam);


        IRenderOption options = null == this.renderOptions ? new RenderOption() : this.renderOptions;

        String format = request.getParameter(this.reportFormatRequestParameter);

        if (format == null) {
            format = "html";
        }

        response.setContentType(birtEngine.getMIMEType(format));

        switch (format.toLowerCase()) {
            case "html":
                HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
                htmlOptions.setOutputFormat("html");
                htmlOptions.setOutputStream(response.getOutputStream());
                htmlOptions.setImageHandler(new HTMLServerImageHandler());
                htmlOptions.setBaseImageURL(request.getContextPath() + "/images");
                htmlOptions.setImageDirectory(sc.getRealPath("/images"));
                runAndRenderTask.setRenderOption(htmlOptions);
                break;

            case "pdf":
                PDFRenderOption pdfOptions = new PDFRenderOption(options);
                pdfOptions.setOutputFormat("pdf");
                pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
                pdfOptions.setOutputStream(response.getOutputStream());
                runAndRenderTask.setRenderOption(pdfOptions);
                break;

            default:
                String att = "download." + format;
                String uReportName = reportFileName.toUpperCase();
                if (uReportName.endsWith(".RPTDESIGN")) {
                    String extension = format;
                    if (format.contains("spudsoft")) {
                        extension = "xls";
                    }
                    att = uReportName.replace(".RPTDESIGN", "." + extension);
                }

                response.setHeader("Content-Disposition", "attachment; filename=\"" + att + "\"");
                options.setOutputStream(response.getOutputStream());
                options.setOutputFormat(format);
                runAndRenderTask.setRenderOption(options);
        }

        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, request);
        runAndRenderTask.run();
        runAndRenderTask.close();
    }

    protected HashMap<String, Object> discoverAndSetParameters(IReportRunnable report,
                                                               HttpServletRequest request) throws Exception {
        HashMap<String, Object> parms = new HashMap<>();
        IGetParameterDefinitionTask task = birtEngine.createGetParameterDefinitionTask(report);
        Collection params = task.getParameterDefns(true);

        for (Object p : params) {
            IParameterDefnBase param = (IParameterDefnBase) p;
            IScalarParameterDefn scalar = (IScalarParameterDefn) param;
            if (request.getParameter(param.getName()) != null) {
                parms.put(param.getName(), getParamValueObject(request, scalar));
            }
        }
        task.close();
        return parms;
    }

    protected Object getParamValueObject(HttpServletRequest request,
                                         IScalarParameterDefn parameterObj) throws Exception {
        String paramName = parameterObj.getName();
        String format = parameterObj.getDisplayFormat();

        if (doesReportParameterExist(request, paramName)) {
            ReportParameterConverter converter = new ReportParameterConverter(format, request.getLocale());
            String paramValue = getReportParameter(request, paramName, null);
            return converter.parse(paramValue, parameterObj.getDataType());
        }
        return null;
    }

    public static String getReportParameter(HttpServletRequest request,
                                            String name, String defaultValue) {
        assert request != null && name != null;

        String value = getParameter(request, name);
        if (value == null || value.length() <= 0) {
            value = "";
        }

        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap == null || !paramMap.containsKey(name)) {
            value = defaultValue;
        }

        Set<String> nullParams = getParameterValues(request, PARAM_ISNULL);

        if (nullParams != null && nullParams.contains(name)) {
            value = null;
        }

        return value;
    }

    public static boolean doesReportParameterExist(HttpServletRequest request,
                                                   String name) {
        assert request != null && name != null;

        boolean isExist = false;

        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap != null) {
            isExist = (paramMap.containsKey(name));
        }

        Set<String> nullParams = getParameterValues(request, PARAM_ISNULL);
        if (nullParams != null && nullParams.contains(name)) {
            isExist = true;
        }

        return isExist;
    }

    public static String getParameter(HttpServletRequest request,
                                      String parameterName) {
        return request.getParameter(parameterName);
    }

    /**
     * Allows setting parameter values to null using __isnull
     */
    public static Set<String> getParameterValues(HttpServletRequest request,
                                                 String parameterName) {
        Set<String> parameterValues = null;
        String[] parameterValuesArray = request.getParameterValues(parameterName);

        if (parameterValuesArray != null) {
            parameterValues = new LinkedHashSet<>();
            Collections.addAll(parameterValues, parameterValuesArray);
        }

        return parameterValues;
    }

    public void setBirtEngine(IReportEngine birtEngine) {
        this.birtEngine = birtEngine;
    }

    private String getPath(URL url) {
        return url.getPath().replaceFirst("^/(.:/)", "$1");
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }
}