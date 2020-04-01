package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InboxController {

    @RequestMapping("/inbox")
    public String index(){
        return "inbox";
    }

}
