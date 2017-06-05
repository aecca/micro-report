package com.bbva.reports.ui.api.controllers;

import com.bbva.reports.engine.ReportEngine;
import com.bbva.reports.engine.model.Report;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Report Rest Controller
 *
 * @author aecca
 */
@RequestMapping("/reports")
@RestController
public class ReportsController {
    private final ReportEngine reportEngine;
    static final Logger logger = Logger.getLogger(ReportsController.class);


    @Autowired
    public ReportsController(ReportEngine reportEngine) {
        this.reportEngine = reportEngine;
    }

    /**
     * Obtener el listado de todos los reportes
     *
     * @return List Lista de los reportes
     * @throws IOException en caso que ocurra un error, se genra una exception I/O.
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Report> listReports() throws IOException {
        logger.info("Report generate  by codApplication = " + "APP001");
        return reportEngine
                .getReportCollection()
                .listReports();
    }

    /**
     * Guardar Reporte personalizado
     *
     * @param report Reporte a guardar
     * @return Map Respuesta del servicio
     * @throws IOException en caso que ocurra un error, se genra una exception I/O
     */
    @RequestMapping(method = RequestMethod.POST)
    public Map saveReport(@RequestBody Report report) throws IOException {

        if(report.name() == null || report.name().isEmpty()) {
            throw new IllegalArgumentException("Report name is required");
        }

        reportEngine.getReportCollection().save(report);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "The report was successfully saved");
        response.put("status", true);

        return response;
    }

    /**
     * Eliminar fisicamente el reporte
     *
     * @param name Nombre del reporte a eliminar
     * @return {@link Map} Respuesta del servicio
     * @throws IOException en caso que ocurra un error, se genra una exception I/O
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "{name}")
    public Map deleteReport(@PathVariable String name) throws IOException {

        reportEngine.getReportCollection().deleteReport(name);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "The report was successfully deleted");
        response.put("status", true);

        return response;
    }


    /**
     * Generar un reporte personalizado en diversos formatos
     *
     * @param reqParams Parametros requeridos del reporte
     * @param reportName Nombre del reporte a obtener
     *
     * @return {@link ModelAndView} Vista final del reporte, que extiende de la vista abstracta de Spring!
     */
    @RequestMapping("/generate")
    public ModelAndView generate(
            @RequestParam Map<String, Object> reqParams,
            @RequestParam("reportName") String reportName) {
        try {
            return new ModelAndView(reportEngine.createReportView(reportName, reqParams));
        } catch (IOException e) {
            throw new IllegalArgumentException("El Reporte solicitado no existe", e);
        }
    }

}