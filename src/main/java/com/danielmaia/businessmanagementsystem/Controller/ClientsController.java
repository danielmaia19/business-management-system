package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Note;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.NoteService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ClientsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private NoteService noteService;

    @GetMapping("/clients")
    public String index(ModelMap model, Client client, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        model.addAttribute("clients", clientService.findClientsByUser(currentUser));
        model.addAttribute("name", currentUser.getFullName());

        return "clients";
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public String createClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult, Authentication authentication) {
        //TODO: Handle errors to show in modal
        if (bindingResult.hasErrors()) {
            return "clients";
        } else {
            User user = (User) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername());

            client.setUser(currentUser);
            clientService.saveClient(client);

            return "redirect:/clients";
        }
    }

    @RequestMapping(path = "/clients/{name}", method = RequestMethod.GET)
    public String viewClientsAndNotes(@PathVariable("name") String name, @ModelAttribute("note") Note note, Model model) {

        Client client = clientService.findByName(name);
        List<Note> notes = noteService.findAllByClientOrderBySubmittedDateDesc(client);


        model.addAttribute("client", client);
        model.addAttribute("notes", notes);

        return "client/view";
    }

    @RequestMapping(value = "/clients/{name}/edit", method = RequestMethod.POST)
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

            return "redirect:/clients/{name}";
        }

    }

    @RequestMapping(value = "/clients/{name}/delete")
    public String updateClient(@PathVariable("name") String name, @ModelAttribute("editClient") @Valid Client client){

        clientService.deleteClient(clientService.findByName(client.getName()));

        System.out.println("executed");

        return "redirect:/clients";
    }

}
