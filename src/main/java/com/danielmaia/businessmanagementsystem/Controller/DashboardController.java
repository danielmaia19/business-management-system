package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // If navigated to the /dashboard URI
    // Then the user will see the dashboard page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

}
