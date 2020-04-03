package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InboxController {

    // Show the inbox page
    @GetMapping("/inbox")
    public String index(){
        return "inbox";
    }

}
