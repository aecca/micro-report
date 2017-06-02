package com.bbva.reports.ui.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/demo")
    public String index() {
        return "index";
    }

}