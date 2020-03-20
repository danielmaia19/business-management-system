package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ClientsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

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
    public String viewClient(@PathVariable("name") String name, Model model) {

        Client client = clientService.findByName(name);

        model.addAttribute("client", client);

        return "clients/view";
    }

}
