package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportsController {

    @GetMapping("/reports")
    public String index() {
        return "reports";
    }

}
