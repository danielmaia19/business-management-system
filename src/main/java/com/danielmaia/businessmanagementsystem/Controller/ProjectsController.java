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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class ProjectsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    //Show all users projects
    @GetMapping("/projects")
    public String index(ModelMap model, Project project, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        List<Client> clients = clientService.findAllByUser(currentUser);
        List<Project> projects = new ArrayList<>();

        for(Client client : clients) {
            projects.addAll(client.getProjects());
        }

        model.addAttribute("name", currentUser.getFullName());
        model.addAttribute("projects", projects);

        return "projects";
    }

    // Show view to add projects
    @GetMapping("/projects/add")
    public String addProjectView(ModelMap model, Project project, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        List<Client> clients = clientService.findAllByUser(currentUser);

        model.addAttribute("clients", clients);
        model.addAttribute("project", project);

        return "project/add";
    }

    // Add new projects
    @PostMapping(path = "/projects")
    public String createClient(@RequestParam("client") String client, @ModelAttribute("project") @Valid Project project, BindingResult bindingResult, Authentication authentication) {
            User user = (User) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername());

            Client selectedClient = clientService.findByName(client);

            System.out.println(selectedClient.getName());

            project.setCreated_on(new Date());

            //project.setUser(currentUser);
            project.setClient(selectedClient);
            projectService.saveProject(project);

            return "redirect:/projects";
    }

    // Individual project view
    @GetMapping(path = "/projects/{name}")
    public String viewProject(@PathVariable("name") String name, Model model) {
        Project project = projectService.findByName(name);
        model.addAttribute("project", project);
        return "project/view";
    }

    @GetMapping("/projects/{name}/edit")
    public String projectEditView(@PathVariable String name, Model model, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        Project project = projectService.findByName(name);
        model.addAttribute("project", project);
        model.addAttribute("projectClient", project.getClient());
        model.addAttribute("clients", clientService.findAllByUser(currentUser));

        return "project/edit";
    }

    @PostMapping("/projects/{name}/edit")
    public String projectEdit(@PathVariable String name, @ModelAttribute("project") Project project, Model model, Authentication authentication) {

        Project foundProject = projectService.findByName(name);

        foundProject.setName(project.getName());
        foundProject.setStatus(project.getStatus());
        foundProject.setProgress(project.getProgress());
        foundProject.setDescription(project.getDescription());
        foundProject.setContactPerson(project.getContactPerson());
        foundProject.setQuotePrice(project.getQuotePrice());
        foundProject.setClient(project.getClient());
        projectService.saveProject(foundProject);

        return "redirect:/projects";
    }

    @PostMapping("/projects/{name}/delete")
    public String projectDelete(@PathVariable String name, Model model, RedirectAttributes redirectAttributes) {

        try {
            projectService.deleteProjectByName(name);
            redirectAttributes.addFlashAttribute("successDeletion", "Successfully removed the project");
        } catch (Exception e) {
            System.out.println("there was an error");
            redirectAttributes.addFlashAttribute("failedDeletion", "Failed to remove the project");
        }

        return "redirect:/projects";
    }

}


