package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.*;
import com.danielmaia.businessmanagementsystem.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ClientsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientFileService clientFileService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientNoteService clientNoteService;

    // Show the clients page and lists all the clients
    @GetMapping("/clients")
    public String index(ModelMap model, Client client, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        List<Client> clients = clientService.findAllByUser(currentUser);
        List<Project> projects = new ArrayList<>();

        for(Client userClient : clients) {
            projects.addAll(userClient.getProjects());
        }

        model.addAttribute("projects", projects);
        model.addAttribute("clients", clientService.findAllByUser(currentUser));
        model.addAttribute("name", currentUser.getFullName());

        return "clients";
    }

    // Save client created by user
    @PostMapping(path = "/clients")
    public String createClient(@ModelAttribute("client") Client client, BindingResult bindingResult, Authentication authentication) {
            User user = (User) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername());

            client.setUser(currentUser);
            clientService.saveClient(client);

            return "redirect:/clients";
    }

    // View selected client, its information and all the notes.
    @GetMapping(path = "/clients/{name}")
    public String viewClientsAndNotes(@PathVariable("name") String name, @ModelAttribute("note") ClientNote clientNote, Model model) {
        Client client = clientService.findByName(name);

        List<ClientFile> clientFiles = clientFileService.findAllByClient(client);

        model.addAttribute("clientFiles", clientFiles);
        model.addAttribute("client", client);
        model.addAttribute("notes", clientNoteService.findAllByClientOrderBySubmittedDateDesc(client));

        return "client/view";
    }

    // Edit client
    @PostMapping(value = "/clients/{name}/edit")
    public String updateClient(@PathVariable("name") String name, @ModelAttribute("editClient") @Valid Client client, BindingResult bindingResult, Authentication authentication){
        //TODO: Handle errors to show in modal
        if (bindingResult.hasErrors()) {
            return "clients";
        } else {
            User user = (User) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername());

            Client updatedClient = clientService.findByName(name);

            updatedClient.setName(client.getName());
            updatedClient.setCity(client.getCity());
            updatedClient.setRegion(client.getRegion());
            updatedClient.setPostCode(client.getPostCode());
            updatedClient.setCountry(client.getCountry());
            updatedClient.setDescription(client.getDescription());
            updatedClient.setContactPerson(client.getContactPerson());
            updatedClient.setAddressLineOne(client.getAddressLineOne());
            updatedClient.setAddressLineTwo(client.getAddressLineTwo());
            updatedClient.setContactPersonEmail(client.getContactPersonEmail());

            updatedClient.setUser(currentUser);

            clientService.saveClient(updatedClient);

            return "redirect:/clients/"+updatedClient.getName();
        }

    }

    // Delete client
    @RequestMapping(value = "/clients/{name}/delete")
    public String updateClient(@PathVariable("name") String name, @ModelAttribute("editClient") @Valid Client client){
        clientService.deleteClient(clientService.findByName(client.getName()));
        return "redirect:/clients";
    }

}
