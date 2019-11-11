package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExpensesController {

    @GetMapping("/expenses")
    public String index() {
        return "expenses";
    }

}
