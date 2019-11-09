package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimesheetsController {

    @GetMapping("/timesheets")
    public String index() {
        return "timesheets";
    }

}
