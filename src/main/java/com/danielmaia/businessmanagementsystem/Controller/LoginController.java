package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

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

    @RequestMapping(value = "/loggedUsers")
    public String admin(Map<String, Object> model) {

        if(sessionRegistry.getAllPrincipals().size() != 0) {
            log.info("ACTIVE USER: " + sessionRegistry.getAllPrincipals().size());
            model.put("activeuser",  sessionRegistry.getAllPrincipals().size());
        }
        else
            log.warn("EMPTY" );

        log.debug(" access ADMIN page. Access granted.");
        return "loggedUsers";
    }

}
