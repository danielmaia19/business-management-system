package com.danielmaia.businessmanagementsystem.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String index() {
        return "login";
    }

    // When the form is submitted this will be actioned.
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginAnother() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return "redirect:/dashboard";
        } else return "login";
    }


}
