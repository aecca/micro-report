package com.bbva.reports.engine.visualizer.birt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BirtAutoConfiguration {

    @Value("${birt.reportsPath}")
    public String reportsPath;

    @Value("${birt.defaultTemplate}")
    public String defaultTemplate;

    @Bean
    public BirtEngineFactory engineFactory() {
        return new BirtEngineFactory();
    }

    @Bean
    public BirtViewFactory viewFactory() throws Exception {

        BirtViewFactory bvf = new BirtViewFactory();
        bvf.setBirtEngine(engineFactory().getObject());
        bvf.setReportsRootPath(reportsPath);
        bvf.setDefaultReportFileName(defaultTemplate);

        return bvf;
    }

}
