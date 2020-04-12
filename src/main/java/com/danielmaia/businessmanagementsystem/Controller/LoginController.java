package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

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

}
