package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Repository.ClientRepository;
import com.danielmaia.businessmanagementsystem.Repository.UserRepository;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

}
