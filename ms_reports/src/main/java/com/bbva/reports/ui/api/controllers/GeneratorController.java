package com.bbva.reports.ui.api.controllers;

import com.bbva.reports.engine.ReportEngine;
import com.bbva.reports.engine.model.ReportSourceType;
import com.bbva.reports.engine.model.ReportSourceTypeCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
public class GeneratorController {
    private ReportSourceTypeCollection reportSourceTypeRepository;
    private final ReportEngine reportService;

    @Autowired
    public GeneratorController(ReportSourceTypeCollection reportSourceTypeRepository, ReportEngine reportService) {
        this.reportSourceTypeRepository = reportSourceTypeRepository;
        this.reportService = reportService;
    }


    @RequestMapping("/resources")
    public List<ReportSourceType> list() {

        return reportSourceTypeRepository.findAll();
    }

    @RequestMapping("/reports")
    public ModelAndView reports(
            @RequestParam Map<String, Object> reqParams,
            @RequestParam("reportId") int reportId) {
        return new ModelAndView(reportService.generateReport(reportId, reqParams));
    }
}