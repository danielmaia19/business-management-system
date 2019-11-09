package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvoicesController {

    @GetMapping("/invoices")
    public String index() {
        return "invoices";
    }


}
