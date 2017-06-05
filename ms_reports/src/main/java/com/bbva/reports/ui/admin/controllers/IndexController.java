package com.bbva.reports.ui.admin.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class IndexController {

    @Value("${server.port}")
    private String appPort;

    @GetMapping("/reports/admin")
    public String index(Map<String, Object> model) {
        model.put("appPort", appPort);
        return "index";
    }

}