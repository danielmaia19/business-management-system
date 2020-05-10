package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.ProjectService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    /**
     * The user will see the dashboard page if they are currently logged in.
     * The user will be able to the number of active projects, project count and clients count.
     * @param model to pass the data from the method to the view
     * @return The dashboard view
     */
    @GetMapping("/dashboard")
    public String index(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();
        User currentUser = userService.findByUsername(username);
        List<Project> usersProjects = new ArrayList<>();

        List<Client> clients = clientService.findAllByUser(currentUser);
        int count = 0;
        int projectsCount = 0;

        for(Client client : clients) {
            count += projectService.countProjectsByClient(client);
            usersProjects.addAll(client.getProjects());
        }

        for(Project project : usersProjects) {
            if(project.getStatus().equals("To do") || project.getStatus().equals("In Progress")) {
                projectsCount++;
            }
        }

        model.addAttribute("activeProjects", projectsCount);
        model.addAttribute("projectsCount", count);
        model.addAttribute("clientsCount", clientService.countAllByUser(currentUser));
        model.addAttribute("name", currentUser.getFullName());
        return "dashboard";
    }

}
