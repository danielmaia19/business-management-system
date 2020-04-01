package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.ProjectService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public String index(ModelMap model, Project project, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        model.addAttribute("name", currentUser.getFullName());
        model.addAttribute("projects", projectService.findProjectsByUser(currentUser));

        return "projects";
    }

    @GetMapping("/projects/add")
    public String addProjectView(ModelMap model, Project project, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        List<Client> clients = clientService.findClientsByUser(currentUser);

        model.addAttribute("clients", clients);
        model.addAttribute("project", project);

        return "project/add";
    }

    @RequestMapping(path = "/projects", method = RequestMethod.POST)
    public String createClient(@RequestParam("client") String client, @ModelAttribute("project") @Valid Project project, BindingResult bindingResult, Authentication authentication) {

            User user = (User) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername());

            Client selectedClient = clientService.findByName(client);

            System.out.println(selectedClient.getName());

            project.setClient(selectedClient);
            project.setUser(currentUser);
            projectService.saveProject(project);

            return "redirect:/projects";

    }

    @RequestMapping(path = "/projects/{name}", method = RequestMethod.GET)
    public String viewProject(@PathVariable("name") String name, Model model) {

        Project project = projectService.findByName(name);

        model.addAttribute("project", project);

        return "project/view";
    }


}


