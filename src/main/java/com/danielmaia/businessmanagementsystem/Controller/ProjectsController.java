package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectsController {

    @GetMapping("/projects")
    public String index() {
        return "projects";
    }


}
