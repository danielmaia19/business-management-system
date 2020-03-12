package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.DAO.UserDao;
import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimesheetsController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/timesheets")
    public String index(ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();

        User currentUser = userDao.findByUsername(username);

        model.addAttribute("name", currentUser.getFullName());

        return "timesheets";
    }

}
