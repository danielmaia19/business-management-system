package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    /**
     * Checks if the user is logged in already
     * If so, they are redirected to the dashboard, otherwise they are redirected to the login page.
     * @return welcome view
     */
    @RequestMapping(path = "/")
    public String index() {
        return "welcome";
    }

}
