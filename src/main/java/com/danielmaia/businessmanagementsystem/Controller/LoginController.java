package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/", "login"})
    public String index(Principal principal) {
        return principal == null ?  "login" : "forward:/dashboard";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user) {
        userService.findByUsername(user.getUsername());
        return "dashboard";
    }

}
