package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignupController {

    UserServiceImpl userService;

    @GetMapping("/signup")
    public String index() {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void registerUser() {

    }

}

