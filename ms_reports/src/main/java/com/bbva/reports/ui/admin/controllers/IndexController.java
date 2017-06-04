package com.bbva.reports.ui.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/reports/admin")
    public String index() {
        return "index";
    }

}