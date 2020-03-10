package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping({"/", "/login"})
    public String index(Principal principal, ModelMap model) {
        model.addAttribute("view", "dashboard");
        return principal == null ?  "login" : "forward:/dashboard";
    }

}
