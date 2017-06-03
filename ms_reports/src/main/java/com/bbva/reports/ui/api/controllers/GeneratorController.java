package com.bbva.reports.ui.api.controllers;

import com.bbva.reports.engine.ReportEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@RestController
public class GeneratorController {
    private final ReportEngine reportEngine;

    @Autowired
    public GeneratorController(ReportEngine reportEngine) {
        this.reportEngine = reportEngine;
    }

    @RequestMapping("/reports")
    public ModelAndView reports(
            @RequestParam Map<String, Object> reqParams,
            @RequestParam("reportName") String reportName) {
        try {
            return new ModelAndView(reportEngine.createReportView(reportName, reqParams));
        } catch (IOException e) {
            throw new IllegalArgumentException("El Reporte solicitado no existe", e);
        }
    }
}