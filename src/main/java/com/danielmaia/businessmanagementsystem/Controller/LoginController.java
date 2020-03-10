package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String index(Principal principal) {
        return principal == null ?  "login" : "redirect:/dashboard";
    }

}
