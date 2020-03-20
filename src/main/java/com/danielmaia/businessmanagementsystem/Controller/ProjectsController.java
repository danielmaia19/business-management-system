package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProjectsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects")
    public String index(ModelMap model, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        User currentUser = userRepository.findByUsername(user.getUsername());

        model.addAttribute("name", currentUser.getFullName());

        //TODO: Display all the projects of the current user
        model.addAttribute("projects", projectRepository.findAll());


        return "projects";
    }

    @RequestMapping(path = "/projects", method = RequestMethod.POST)
    public String createProject(@ModelAttribute("project") Project project, BindingResult bindingResult, Authentication authentication) {

        User user = (User)authentication.getPrincipal();
        User currentUser = userRepository.findByUsername(user.getUsername());

        if(bindingResult.hasErrors()) {
            return "projects";
        }

        return "projects";
    }

}
