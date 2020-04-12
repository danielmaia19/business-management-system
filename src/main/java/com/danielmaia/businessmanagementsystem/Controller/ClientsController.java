package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.*;
import com.danielmaia.businessmanagementsystem.Service.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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
    public String createClient(Model model, @ModelAttribute("client") Client client, RedirectAttributes redirectAttributes, Authentication authentication) {
            User user = (User) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername());

            if(clientService.existsByName(client.getName())) {
                System.out.println("Client already exists");
                redirectAttributes.addFlashAttribute("error", "client already exists");
                return "redirect:/clients";
            } else {
                client.setUser(currentUser);
                clientService.saveClient(client);
                return "redirect:/clients";
            }
    }

    // View selected client, its information and all the notes.
    @GetMapping(path = "/clients/{name}")
    public String viewClientsAndNotes(@PathVariable("name") String name, @ModelAttribute("note") ClientNote clientNote, Model model) {
        Client client = clientService.findByName(name);

        List<ClientFile> clientFiles = clientFileService.findAllByClient(client);
        List<Project> projects = projectService.findAllByClient(client);
        
        BigDecimal totalQuoted = new BigDecimal(0);

        for(Project project : projects) {
            totalQuoted = totalQuoted.add(project.getQuotePrice());
        }

        model.addAttribute("totalQuoted", totalQuoted);
        model.addAttribute("clientFiles", clientFiles);
        model.addAttribute("client", client);
        model.addAttribute("notes", clientNoteService.findAllByClientOrderBySubmittedDateDesc(client));

        return "client/view";
    }

    @GetMapping("/clients/{name}/chart")
    @ResponseBody
    public String lineChart(@PathVariable("name") String name) {
        Client client = clientService.findByName(name);
        List<Project> projects = projectService.findAllByClient(client);
        LinkedHashMap<String, Integer> prevTwelveMonths = new LinkedHashMap<>();

        JsonArray jsonMonth = new JsonArray();
        JsonArray jsonProjectCount = new JsonArray();
        JsonObject json = new JsonObject();

        // Sorts the projects by date
        projects.sort(new Comparator<Project>() {

            @Override
            public int compare(Project o1, Project o2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                    return sdf.parse(o1.getCreatedOn().toString()).compareTo(sdf.parse(o2.getCreatedOn().toString()));  //sdf.parse returns date - So, Compare Date with date
                } catch (ParseException ex) {
                    return o1.getCreatedOn().toString().compareTo(o2.getCreatedOn().toString());
                }
            }
        });

        for(Project project : projects) {
            Date projectDate = project.getCreatedOn();
            LocalDate date = new LocalDate(projectDate);
            String month = date.monthOfYear().getAsText();

            if(prevTwelveMonths.size() < 12) {
                if(!prevTwelveMonths.containsKey(month)) {
                    prevTwelveMonths.put(month,1);
                } else {
                    prevTwelveMonths.replace(month, prevTwelveMonths.get(month)+1);
                }
            } else {
                // Removes the first month when the 12 month has been reached
                System.out.println(prevTwelveMonths.entrySet().toArray()[0]);
            }
        }

        prevTwelveMonths.forEach((key, value) -> {
            jsonMonth.add(key);
            jsonProjectCount.add(value);
        });

        json.add("month", jsonMonth);
        json.add("count", jsonProjectCount);
        return json.toString();
    }

    // Edit client
    @PostMapping(value = "/clients/{name}/edit")
    public String updateClient(@PathVariable("name") String name, @ModelAttribute("editClient") @Valid Client client, BindingResult bindingResult, Authentication authentication){

        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());


        //TODO: Handle errors to show in modal
        if (bindingResult.hasErrors()) {
            return "clients";
        } else {

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
