package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.HelperClasses.AuthenticationSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    // This is the root page.
    // At the moment the homepage gets redirected to the login page
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }


    // The login page will be where users login.
    // It first checks if the user is already logged in.
    // If they are then they are redirected to their dashboard
    // If not then to the login screen.
    @GetMapping("login")
    public String login() {
        if (!AuthenticationSystem.isLogged()) return "login"; // or some logic
        return "redirect:/dashboard";
    }


    // This will check if the user is already logged in.
    // If they are, then they get redirected to the '/dashboard' page.
    // Otherwise they will be redirected to the 'login'
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String logUserIn() {
        if (!AuthenticationSystem.isLogged())  return "redirect:/dashboard";
        return "login";
    }


}
