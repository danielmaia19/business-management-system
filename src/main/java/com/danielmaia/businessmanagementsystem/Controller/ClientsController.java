package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.*;
import com.danielmaia.businessmanagementsystem.Service.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Map<Client, Boolean> clientsAndLogos = new HashMap<>();

        for(Client userClient : clients) {
            projects.addAll(userClient.getProjects());

            Path path = Paths.get("src/main/resources/static/logos/" + userClient.getName());

            // Checks if the directory exists
            clientsAndLogos.put(userClient, Files.exists(path));
        }

        model.addAttribute("projects", projects);
        model.addAttribute("clients", clientsAndLogos);
        model.addAttribute("name", currentUser.getFullName());

        return "clients";
    }

    // Save client created by user
    @PostMapping(path = "/clients")
    public String createClient(Model model, @ModelAttribute("client") Client client, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes, Authentication authentication) throws Exception {
            User user = (User) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername());

        if (clientService.existsByName(client.getName())) {
            System.out.println("Client already exists");
            redirectAttributes.addFlashAttribute("error", "client already exists");
            return "redirect:/clients";
        } else {
            client.setUser(currentUser);

            if (!imageFile.isEmpty()) {
                if(imageFile.getContentType().equals("image/jpeg") || imageFile.getContentType().equals("image/png")) {
                    clientService.saveImage(client.getName(), imageFile);
                } else {
                    // Not a image file
                    System.out.println("There was an error");
                }
                
            }

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
        BigDecimal remainingBalance = new BigDecimal(0);

        if(client.getTotalAmountPaid() != null) {

            for (Project project : projects) {
                totalQuoted = totalQuoted.add(project.getQuotePrice());
            }

            remainingBalance = totalQuoted.subtract(client.getTotalAmountPaid());
        }

        boolean fileExists = false;
        Path path = Paths.get("src/main/resources/static/logos/" + name);

        // Checks if the directory exists
        if(Files.exists(path)) {
            fileExists = true;
        }

        model.addAttribute("fileExists", fileExists);
        model.addAttribute("remainingBalance", remainingBalance);
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
    public String deleteClient(@PathVariable("name") String name, @ModelAttribute("editClient") @Valid Client client, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes, Authentication authentication) throws Exception {

        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        if (!client.getName().equals(name) && clientService.existsByName(client.getName())) {
            System.out.println("the client already exists");
            redirectAttributes.addFlashAttribute("duplicateClient", "The client already exists");
            return "redirect:/clients/" + name;
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

            if (!imageFile.isEmpty()) {
                if(imageFile.getContentType().equals("image/jpeg") || imageFile.getContentType().equals("image/png")) {
                    FileUtils.deleteDirectory(new File("src/main/resources/static/logos/" + name));
                    clientService.saveImage(client.getName(), imageFile);
                } else {
                    // Not a image file
                    System.out.println("There was an error");
                }

            }

            clientService.saveClient(updatedClient);

            return "redirect:/clients/" + updatedClient.getName();
        }
    }

    // Delete client
    @RequestMapping(value = "/clients/{name}/delete")
    public String deleteClient(@PathVariable("name") String name, @ModelAttribute("editClient") @Valid Client client) throws IOException {
        FileUtils.deleteDirectory(new File("src/main/resources/static/logos/" + name));
        clientService.deleteClient(clientService.findByName(client.getName()));
        return "redirect:/clients";
    }

    // Add Payment client
    @PostMapping(value = "/clients/{name}/payment")
    public String addPayment(@PathVariable("name") String name, @ModelAttribute("client") @Valid Client client, Authentication authentication) {

        Client updatedClient = clientService.findByName(name);
        List<Project> projects = projectService.findAllByClient(updatedClient);

        BigDecimal totalQuoted = new BigDecimal(0);

        for (Project project : projects) {
            totalQuoted = totalQuoted.add(project.getQuotePrice());
        }

        updatedClient.setTotalAmountPaid(client.getTotalAmountPaid());

        clientService.saveClient(updatedClient);

        return "redirect:/clients/" + updatedClient.getName();
    }

    // Add note
    @PostMapping(path = "/clients/{name}/add")
    public String postNote(Model model, @ModelAttribute("note") @Valid ClientNote clientNote, BindingResult result, @PathVariable("name") String name) {

        if(result.hasErrors()) {
            return "redirect:/clients/{name}?error=Field cannot be empty";
        }

        java.time.LocalDate today = java.time.LocalDate.now();

        Client client = clientService.findByName(name);
        clientNote.setClient(client);
        clientNote.setSubmittedDate(today);
        clientNoteService.saveNote(clientNote);

        model.addAttribute("client", client);
        model.addAttribute("note", clientNote);

        return "redirect:/clients/{name}";
    }

    // Delete note
    @PostMapping(path = "/clients/{name}/note/{id}/delete")
    public String deleteNote(Model model, @ModelAttribute("note") ClientNote clientNote, @PathVariable("name") String name, @PathVariable("id") Long id) {
        clientNoteService.deleteNote(clientNoteService.findNoteById(id));

        return "redirect:/clients/{name}";
    }

    // Edit note
    //TODO: Allow users to edit their notes
    @PostMapping(path = "/clients/{name}/note/{id}/edit")
    public String editNote(Model model, @ModelAttribute("note") ClientNote clientNote, @PathVariable("name") String name, @PathVariable("id") Long id) {
        return "redirect:/clients/{name}";
    }

    @PostMapping("/clients/{name}/upload")
    public String fileUpload(@PathVariable("name") String name, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        Client client = clientService.findByName(name);
        ClientFile clientFileName = clientFileService.saveFile(client, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/clients/" + name + "/downloads/")
                .path(clientFileName.getClientFileId())
                .toUriString();

        //log.info(fileDownloadUri);

        redirectAttributes.addFlashAttribute("uploaded", "File was successfully uploaded");

        return "redirect:/clients/"+name;
    }

    @PostMapping("/clients/{name}/downloads/{clientFileId:.+}/delete")
    public String deleteFile(@PathVariable String name, @PathVariable String clientFileId, RedirectAttributes redirectAttributes) {

        clientFileService.deleteFile(clientFileId);

        redirectAttributes.addFlashAttribute("deleted", "File was successfully uploaded");

        return "redirect:/clients/"+name;
    }

    //@PostMapping("/multipleFiles")
    //public List<File> uploadingMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    //    return Arrays.asList(files).stream().map(file -> testingUpload("test", file)).collect(Collectors.toList());
    //}

}
