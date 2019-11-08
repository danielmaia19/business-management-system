package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    // No reason for this to be here, can be deleted later on, if not required.
    //@RequestMapping("/")
    //public String index() {
    //    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //    if (auth.getPrincipal() instanceof UserDetails) {
    //        return "redirect:/dashboard";
    //    } else return "login";
    //}

    @GetMapping("login")
    public String index() {
        return "login";
    }

    @RequestMapping(path = "/login")
    public String login(Model model, HttpServletRequest request) {

        model.addAttribute("user", new User());


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return "redirect:/dashboard";
        } else return "login";
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
