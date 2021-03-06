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
public class ReportsController {

    @Autowired
    private UserService userService;

    // Show reports page

    /**
     * Displays the reports page if the user is authenticated.
     * @param model used to add the attribute of the users full name
     * @return Reports view is shown
     */
    @GetMapping("/reports")
    public String index(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();

        User currentUser = userService.findByUsername(username);

        model.addAttribute("name", currentUser.getFullName());

        return "reports";
    }

}
