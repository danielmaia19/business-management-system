package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.DAO.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SignupController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/signup")
    public String index(ModelAndView modelAndView, User user) {
        //
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User)authentication.getPrincipal();
        //String username = user.getUsername();
        //
        //User currentUser = userRepository.findByUsername(username);
        //
        //model.addAttribute("name", currentUser.getFullName());

        //modelAndView.addObject("user", user);
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") @Valid User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "dashboard";
    }

}

