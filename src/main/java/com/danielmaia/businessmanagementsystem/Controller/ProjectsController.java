package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Repository.ProjectRepository;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.ProjectService;
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

import javax.validation.Valid;

@Controller
public class ProjectsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/projects")
    public String index(ModelMap model, Project project, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        User currentUser = userRepository.findByUsername(user.getUsername());

        model.addAttribute("name", currentUser.getFullName());
        model.addAttribute("projects", projectService.findProjectsByUser(currentUser));

        return "projects";
    }

    @RequestMapping(path = "/projects", method = RequestMethod.POST)
    public String createClient(@ModelAttribute("project") @Valid Project project, BindingResult bindingResult, Authentication authentication) {

        //TODO: Handle errors to show in modal
        if (bindingResult.hasErrors()) {
            return "projects";
        } else {
            User user = (User) authentication.getPrincipal();
            User currentUser = userRepository.findByUsername(user.getUsername());

            project.setUser(currentUser);
            projectService.saveProject(project);

            return "redirect:/projects";
        }


    }

}
