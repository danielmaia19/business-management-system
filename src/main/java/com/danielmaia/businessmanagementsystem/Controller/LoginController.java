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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    // Show login page
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

    @GetMapping("/loggedUsers")
    public String admin() {
        return "loggedUsers";
    }

    @GetMapping("/loggedUsers/total")
    @ResponseBody
    public String admin(Map<String, Object> model) {

        //if(sessionRegistry.getAllPrincipals().size() != 0) {
        //    log.info("ACTIVE USER: " + sessionRegistry.getAllPrincipals().size());
        //    model.put("activeuser",  sessionRegistry.getAllPrincipals().size());
        //}
        //else
        //    log.warn("EMPTY" );
        //
        //log.debug(" access ADMIN page. Access granted.");


        JsonArray jsonActiveUsers = new JsonArray();
        JsonObject json = new JsonObject();

        jsonActiveUsers.add(sessionRegistry.getAllPrincipals().size()-1);

        json.add("activeUsers", jsonActiveUsers);

        return json.toString();
    }

}
