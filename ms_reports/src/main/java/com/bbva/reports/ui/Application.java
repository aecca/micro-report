package com.bbva.reports.ui;

import com.bbva.reports.engine.ReportEngine;
import com.bbva.reports.engine.ReportViewFactory;
import com.bbva.reports.engine.datasource.JSONDataSource;
import com.bbva.reports.engine.implement.JsonReportCollection;
import com.bbva.reports.engine.model.Report;
import com.bbva.reports.engine.template.MustacheEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@ImportResource({
        "classpath:application-config.xml",
        "classpath:reports-config.xml"
})
@EntityScan("com.bbva.reports.engine.model")
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class
})
public class Application {

    public static void main(String[] args) throws IOException {
//        ReportEngine engine = new ReportEngine(
//              new JSONDataSource(),
//              new MustacheEngine(),
//              new JsonReportCollection("/reports"),
//              new ReportViewFactory()
//        );
//
//        Report demo = demo = engine.getReportCollection().openReport("demo");;
//        System.out.println(demo);
        SpringApplication.run(Application.class, args);
    }
}