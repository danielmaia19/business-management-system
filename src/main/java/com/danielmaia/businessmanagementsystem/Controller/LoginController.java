package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.HelperClasses.AuthenticationSystem;
import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String index(Principal principal) {
        return principal == null ?  "login" : "redirect:/";
    }

}
