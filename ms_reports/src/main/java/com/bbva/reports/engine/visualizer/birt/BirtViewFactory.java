package com.bbva.reports.engine.visualizer.birt;

import org.eclipse.birt.report.engine.api.IReportEngine;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Factory bean for the instance of the {@link IReportEngine report engine}.
 *
 * @author aecca
 */
public class BirtViewFactory implements ApplicationContextAware {

    private ApplicationContext context;
    private IReportEngine birtEngine;
    private String reportsRootPath;
    private String defaultReportFileName;

    public IReportEngine getBirtEngine() {
        return birtEngine;
    }

    public void setBirtEngine(IReportEngine birtEngine) {
        this.birtEngine = birtEngine;
    }

    public String getReportsRootPath() {
        return reportsRootPath;
    }

    public void setReportsRootPath(String reportsRootPath) {
        this.reportsRootPath = reportsRootPath;
    }

    public void setDefaultReportFileName(String template) {
        defaultReportFileName = template;
    }

    public BirtView createBirtView() {
        BirtView bw = new BirtView();
        bw.setBirtEngine(birtEngine);
        bw.setReportsRootPath(getReportsRootPath());

        if (null != defaultReportFileName) {
            bw.setReportFileName(defaultReportFileName);
        }

        return bw;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}