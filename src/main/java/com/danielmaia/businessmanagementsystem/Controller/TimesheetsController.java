package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
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
    private UserRepository userRepository;

    // Show timesheet page

    /**
     * Displays the timesheets page if the user is authenticated.
     * @param model
     * @return Timesheets view page.
     */
    @GetMapping("/timesheets")
    public String index(ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();

        User currentUser = userRepository.findByUsername(username);

        model.addAttribute("name", currentUser.getFullName());

        return "timesheets";
    }
}
