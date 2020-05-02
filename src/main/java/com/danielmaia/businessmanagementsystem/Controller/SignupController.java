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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Displays the sign up page for users to register an account.
     * @param user Passed to the view for the handling of the form to create an account.
     * @return Signup view page.
     */
    @GetMapping("/signup")
    public String index(User user) {
        return "signup";
    }

    // Add new signup user

    /**
     * The user will be shown either the signup page if they are unsuccessful or their dashboard is shown if successful.
     * @param user Information from the user to create the account.
     * @param bindingResult To show errors if there are any.
     * @param request
     * @return Either the dashboard or login depending if there are successful or not.
     */
    @PostMapping(path = "/signup")
    public String create(@ModelAttribute @Valid User user, BindingResult bindingResult, HttpServletRequest request) {

        User usernameExists = userService.findByUsername(user.getUsername());
        User emailExists = userService.findByEmail(user.getEmail());

        if(usernameExists != null) {
            bindingResult.addError(new FieldError("username","username", "Username already exists"));
            return "signup";
        }

        if(emailExists != null) {
            bindingResult.addError(new FieldError("email","email", "Email is already associated to an account"));
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

