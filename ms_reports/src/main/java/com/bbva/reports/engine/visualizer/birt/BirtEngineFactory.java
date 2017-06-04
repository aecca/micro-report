package com.bbva.reports.engine.visualizer.birt;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

/**
 * Factory bean for the instance of the {@link IReportEngine report engine}.
 *
 * @author aecca
 */
public class BirtEngineFactory {

    private IReportEngine birtEngine;
    private java.util.logging.Level logLevel;

    public IReportEngine createEngine() {
        EngineConfig config = new EngineConfig();

        config.setLogConfig(null, this.logLevel);
        try {
            Platform.startup(config);
        } catch (BirtException e) {
            throw new RuntimeException("Could not start the Birt engine!", e);
        }

        IReportEngineFactory factory = (IReportEngineFactory)
                Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);

        return factory.createReportEngine(config);
    }

}
