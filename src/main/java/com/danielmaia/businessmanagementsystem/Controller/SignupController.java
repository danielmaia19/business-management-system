package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Role;
import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.management.remote.JMXAuthenticator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class SignupController {

    @Autowired
    UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    protected AuthenticationManager authenticationManager;


    @RequestMapping("/signup")
    public String index(ModelAndView modelAndView, User user) {
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelAndView modelAndView, HttpServletRequest request) {


        if(bindingResult.hasErrors()) {
            return "signup";
        } else {

            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setRole(roleRepository.findByName("ROLE_ADMIN"));
            userService.saveUser(user);

            authWithHttpServletRequest(request, user.getUsername(), password);

            return "redirect:/dashboard";
        }

    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            System.out.println(e);
        }
    }

}

