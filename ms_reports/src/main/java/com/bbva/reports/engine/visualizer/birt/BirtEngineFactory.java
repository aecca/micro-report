package com.bbva.reports.engine.visualizer.birt;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

/**
 * Factory bean for the instance of the {@link IReportEngine report engine}.
 *
 * @author aecca
 */
public class BirtEngineFactory implements FactoryBean<IReportEngine>, ApplicationContextAware, DisposableBean {

    private ApplicationContext context;
    private IReportEngine birtEngine;
    private Resource logDirectory;
    private File _resolvedDirectory;
    private java.util.logging.Level logLevel;

    @Override
    public IReportEngine getObject() throws Exception {
        EngineConfig config = new EngineConfig();

        config.getAppContext().put("spring", this.context);
        config.setLogConfig(null != this._resolvedDirectory ? this._resolvedDirectory.getAbsolutePath() : null, this.logLevel);
        try {
            Platform.startup(config);
        } catch (BirtException e) {
            throw new RuntimeException("Could not start the Birt engine!", e);
        }

        IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        IReportEngine be = factory.createReportEngine(config);
        this.birtEngine = be;

        return be;
    }

    @Override
    public Class<?> getObjectType() {
        return IReportEngine.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        birtEngine.destroy();
        Platform.shutdown();
    }

    public void setLogLevel(java.util.logging.Level ll) {
        this.logLevel = ll;
    }

    private void validateLogDirectory(File f) {
        Assert.notNull(f, " the directory must not be null");
        Assert.isTrue(f.isDirectory(), " the path given must be a directory");
        Assert.isTrue(f.exists(), "the path specified must exist!");
    }

    public void setLogDirectory(Resource resource) {
        File f = null;
        try {
            f = resource.getFile();
            validateLogDirectory(f);
            this._resolvedDirectory = f;
        } catch (IOException e) {
            throw new RuntimeException("couldn’t set the log directory");
        }
    }

    public void setLogDirectory(File f) {
        validateLogDirectory(f);
        this._resolvedDirectory = f;
    }
}
