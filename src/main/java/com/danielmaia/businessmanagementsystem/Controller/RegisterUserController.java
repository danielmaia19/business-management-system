package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterUserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/register-user")
    public String index() {
        return "register-user";
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user) {
        userServiceImpl.saveNewRegisteredUser(user);
        return "dashboard";
    }

}

