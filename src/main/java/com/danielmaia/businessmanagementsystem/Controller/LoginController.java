package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Displays the login page. Creates a ADMIN role if one does not exists and then checks if the user has already logged in
     * if they have then they are redirected to the dashboard. If not, they will shown the login page.
     * @return Dashboard or login pages.
     */
    @GetMapping(value = "/login")
    public String index() {
        Role roleExists = roleRepository.findByName("ROLE_ADMIN");
        Role role = new Role("ROLE_ADMIN");

        if(roleExists == null) {
            roleRepository.save(role);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return "forward:/dashboard";
        } else return "login";
    }

    /**
     * Displays the admin view for the Super Admin user (Developers only) to view the number of active sessions
     * to view the number of active users.
     * @param model used to provide the number of active users.
     * @return Logged users view is shown, for only Super Admin users.
     */
    @GetMapping("/loggedUsers")
    public String adminView(Model model) {
        model.addAttribute("numberOfActiveUsers", sessionRegistry.getAllPrincipals().size()-1);
        return "loggedUsers";
    }

    /**
     * REST created to display the number of active users using AJAX from the view.
     * @return JSON represented as a string.
     */
    @ResponseBody
    @GetMapping("/loggedUsers/total")
    public String adminTotalRecords() {
        JsonArray jsonActiveUsers = new JsonArray();
        JsonObject json = new JsonObject();
        jsonActiveUsers.add(sessionRegistry.getAllPrincipals().size()-1);
        json.add("activeUsers", jsonActiveUsers);
        return json.toString();
    }

}
