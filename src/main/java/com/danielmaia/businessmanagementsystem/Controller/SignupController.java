package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Repository.RoleRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.RoleService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/signup")
    public String index(ModelAndView modelAndView, User user) {
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelAndView modelAndView, HttpServletRequest request) {

        User usernameExists = userService.findByUsername(user.getUsername());
        User emailExists = userService.findByEmail(user.getEmail());

        if(usernameExists != null) {
            bindingResult.addError(new FieldError("username","username", "Username already exists"));
            return "signup";
        }

        if(emailExists != null) {
            bindingResult.addError(new FieldError("email","email", "Email already exists"));
            return "signup";
        }

        if (bindingResult.hasErrors()) {
            return "signup";

        } else {
            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setRole(roleService.findByName("ROLE_ADMIN"));
            userService.saveUser(user);

            try {
                request.login(user.getUsername(), password);
            } catch (ServletException e) {
                e.printStackTrace();
            }

            return "redirect:/dashboard";
        }

    }
}

