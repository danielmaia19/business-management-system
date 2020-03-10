package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.DAO.UserDao;
import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
    
    @Autowired
    UserDao userDao;

    // If navigated to the /dashboard URI
    // Then the user will see the dashboard page
    @GetMapping("/dashboard")
    public String index(ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();

        User currentUser = userDao.findByUsername(username);

        model.addAttribute("name", currentUser.getFullName());

        return "dashboard";
    }

}
