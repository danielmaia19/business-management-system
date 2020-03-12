package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.DAO.UserDao;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignupController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/signup")
    public String index(ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();

        User currentUser = userDao.findByUsername(username);

        model.addAttribute("name", currentUser.getFullName());

        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user) {
        userServiceImpl.saveNewRegisteredUser(user);
        return "dashboard";
    }

}

