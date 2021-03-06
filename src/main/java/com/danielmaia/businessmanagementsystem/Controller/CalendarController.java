package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {

    @Autowired
    private UserService userService;

    // Show the calendar page

    /**
     * The index displays the main page for the calendar
     * @param model to pass the data from the method to the view
     * @return The calendar view
     */
    @GetMapping("/calendar")
    public String index(ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();

        User currentUser = userService.findByUsername(username);

        model.addAttribute("name", currentUser.getFullName());

        return "calendar";
    }

}
