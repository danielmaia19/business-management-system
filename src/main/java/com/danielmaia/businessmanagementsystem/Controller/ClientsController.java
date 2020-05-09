package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.*;
import com.danielmaia.businessmanagementsystem.Repository.ClientRepository;
import com.danielmaia.businessmanagementsystem.Service.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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
import java.util.stream.Collectors;


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

    public String logoPath = "uploads/logos/";

    /**
     * Displays the main clients page by showing the lists all the clients
     *
     * @param model          to pass the data from the method to the view
     * @param client
     * @param authentication To get the current logged in user.
     * @return
     */
    @GetMapping("/clients")
    public String index(ModelMap model, Client client, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        List<Client> clients = clientService.findAllByUser(currentUser);
        List<Project> projects = new ArrayList<>();
        Map<Client, Boolean> clientsAndLogos = new HashMap<>();

        for (Client userClient : clients) {
            projects.addAll(userClient.getProjects());

            Path path = Paths.get(logoPath + currentUser.getUsername() + "/" + userClient.getName());

            // Checks if the directory exists
            clientsAndLogos.put(userClient, Files.exists(path));
        }

        model.addAttribute("projects", projects);
        model.addAttribute("clients", clientsAndLogos);
        model.addAttribute("clientsList", clients);
        model.addAttribute("name", currentUser.getFullName());
        model.addAttribute("username", currentUser.getUsername());

        return "clients";
    }

    /**
     * Creates the client created by the user.
     *
     * @param model              to pass the data from the method to the view
     * @param client             The client information recieved from the view to save the client when created.
     * @param imageFile          The image file uploaded by the user.
     * @param redirectAttributes To display any errors.
     * @param authentication     To get the current logged in user.
     * @return To the clients main page by showing the /clients view.
     * @throws Exception
     */
    @PostMapping(path = "/clients")
    public String createClient(Model model, @ModelAttribute("client") Client client,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               RedirectAttributes redirectAttributes,
                               Authentication authentication) throws Exception {

        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        List<Client> clients = clientService.findAllByUser(currentUser);

        boolean clientForUserExists = false;

        for (Client client1 : clients) {
            clientForUserExists = client1.getName().equals(client.getName());
        }

        if (clientForUserExists) {
            redirectAttributes.addFlashAttribute("error", "client already exists");
        } else {
            client.setUser(currentUser);
            client.setTotalAmountPaid(new BigDecimal(0));

            if (!imageFile.isEmpty()) {
                if (imageFile.getContentType().equals("image/jpeg") || imageFile.getContentType().equals("image/png")) {
                    clientService.saveImage(currentUser.getUsername(), client.getName(), imageFile);
                } else {
                    // Not a image file
                    System.out.println("There was an error");
                }
            }

            clientService.saveClient(client);
        }
        return "redirect:/clients";
    }

    /**
     * Displays each individual clients view along with all the notes and so on.
     *
     * @param name       The client name
     * @param clientNote to be used in the forms as a model attribute to then be handled by another method.
     * @param model      to pass the data from the method to view.
     * @return To the specific clients view.
     */
    @GetMapping(path = "/clients/{name}")
    public String viewClientsAndNotes(@PathVariable("name") String name,
                                      @ModelAttribute("note") ClientNote clientNote, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        Client client = clientService.findByName(name);

        List<ClientFile> clientFiles = clientFileService.findAllByClient(client);
        List<Project> projects = projectService.findAllByClient(client);

        BigDecimal totalQuoted = new BigDecimal(0);
        BigDecimal remainingBalance = new BigDecimal(0);

        for (Project project : projects) {
            totalQuoted = totalQuoted.add(project.getQuotePrice());
        }

        if (client.getTotalAmountPaid() != null) {
            remainingBalance = totalQuoted.subtract(client.getTotalAmountPaid());
        }

        boolean fileExists = false;
        Path path = Paths.get(logoPath + currentUser.getUsername() + "/" + name);

        // Checks if the directory exists
        if (Files.exists(path)) {
            fileExists = true;
        }

        model.addAttribute("fileExists", fileExists);
        model.addAttribute("remainingBalance", remainingBalance);
        model.addAttribute("totalQuoted", totalQuoted);
        model.addAttribute("clientFiles", clientFiles);
        model.addAttribute("client", client);
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("notes", clientNoteService.findAllByClientOrderBySubmittedDateDesc(client));

        return "client/view";
    }

    /**
     * REST method to provide the JavaScript the data from this method to the Highchart library.
     *
     * @param name The name of the client passed as a parameter.
     * @return JSON objects as strings
     */
    @ResponseBody
    @GetMapping("/clients/{name}/chart")
    public String lineChart(@PathVariable String name) {
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

        for (Project project : projects) {
            Date projectDate = project.getCreatedOn();
            LocalDate date = new LocalDate(projectDate);
            String month = date.monthOfYear().getAsText();

            if (prevTwelveMonths.size() < 12) {
                if (!prevTwelveMonths.containsKey(month)) {
                    prevTwelveMonths.put(month, 1);
                } else {
                    prevTwelveMonths.replace(month, prevTwelveMonths.get(month) + 1);
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

    /**
     * Updates the client with new information.
     * First the new client name is checked to make sure it does not already exists, if it does not then the the client is found and updated.
     *
     * @param name               The name of the client passed as a parameter.
     * @param client             The client information recieved from the view to update and save the new client information.
     * @param imageFile          Uploaded client logo upload if one is uploaded.
     * @param redirectAttributes If any errors occur then it is passed to the view.
     * @param authentication     Gets the current logged in user.
     * @return Redirected to the client view.
     * @throws Exception
     */
    @PostMapping(value = "/clients/{name}/edit")
    public String updateClient(@PathVariable String name, @ModelAttribute("editClient") @Valid Client client,
                               @RequestParam MultipartFile imageFile,
                               RedirectAttributes redirectAttributes, Authentication authentication,
                               @RequestParam Map<String, String> requestParams) throws Exception {

        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        Client newClient = clientService.findByName(client.getName());

        Client prevClient = clientService.findClientByClientId(Long.parseLong(requestParams.get("id")));

        if (!newClient.getClientId().equals(Long.valueOf(requestParams.get("id")))) {
            redirectAttributes.addFlashAttribute("duplicateClient", "The client already exists");
            return "redirect:/clients/" + prevClient.getName();
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
                if (imageFile.getContentType().equals("image/jpeg") || imageFile.getContentType().equals("image/png")) {
                    FileUtils.deleteDirectory(new File(logoPath + currentUser.getUsername() + "/" + name));
                    clientService.saveImage(currentUser.getUsername(), client.getName(), imageFile);
                } else {
                    // Not a image file, it was something else .txt etc...
                    System.out.println("There was an error");
                }

            }

            clientService.saveClient(updatedClient);
            return "redirect:/clients/" + updatedClient.getName();
        }
    }

    /**
     * Deletes the client and any files uploaded by the client
     *
     * @param name   The name of the client passed as parameter
     * @param client The client information passed from the from the view
     * @return Redirects the user to the clients page
     * @throws IOException
     */
    @RequestMapping(value = "/clients/{name}/delete")
    public String deleteClient(@PathVariable String name,
                               @ModelAttribute("editClient") @Valid Client client,
                               Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        FileUtils.deleteDirectory(new File(logoPath + currentUser.getUsername() + "/" + name));
        clientService.deleteClient(clientService.findByName(client.getName()));
        return "redirect:/clients";
    }

    /**
     * The input value of an amount placed by the user representing the amount the client has paid
     *
     * @param name   The name of the client passed as parameter
     * @param client Information passed from the form
     * @return Redirect the user to the path of the client.
     */
    @PostMapping(value = "/clients/{name}/payment")
    public String addAmountPaidInput(@PathVariable String name, @ModelAttribute @Valid Client client) {

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

    /**
     * Posts the note created by the user for the client
     *
     * @param model  to pass the data from the method to view.
     * @param note   The note passed from the form to be handled
     * @param result To pass any errors to the view
     * @param name   Passed as a path variable to get the client by name.
     * @return
     */
    @PostMapping(path = "/clients/{name}/add")
    public String postNote(Model model, @ModelAttribute @Valid ClientNote note, BindingResult result,
                           @PathVariable String name) {

        if (result.hasErrors()) {
            return "redirect:/clients/{name}?error=Field cannot be empty";
        }

        Client client = clientService.findByName(name);
        note.setClient(client);
        note.setSubmittedDate(java.time.LocalDate.now());
        clientNoteService.saveNote(note);

        model.addAttribute("client", client);
        model.addAttribute("note", note);

        return "redirect:/clients/{name}";
    }


    /**
     * Deletes note by finding it by ID and then deletes it.
     *
     * @param note The client note passed from the view template
     * @param id   The id passed as part of the path variable from the form.
     * @return Redirect the user to the same page of clients/{name}
     */
    @PostMapping(path = "/clients/{name}/note/{id}/delete")
    public String deleteNote(@ModelAttribute ClientNote note, @PathVariable Long id) {
        clientNoteService.deleteNote(clientNoteService.findNoteById(id));
        return "redirect:/clients/{name}";
    }

    /**
     * The client is first found by using the path variable of name and then saving the file upload
     * for the client, accepts any type of file.
     *
     * @param name               The name of the client passed as a parameter.
     * @param file               The file uploaded by the user.
     * @param redirectAttributes To show flash message in the view
     * @return Redirect to the clients page with the path of clients/{name}
     * @throws IOException
     */
    @PostMapping("/clients/{name}/upload")
    public String fileUpload(@PathVariable String name, @RequestParam MultipartFile file,
                             RedirectAttributes redirectAttributes) throws IOException {
        Client client = clientService.findByName(name);
        ClientFile clientFileName = clientFileService.saveFile(client, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/clients/" + name + "/downloads/")
                .path(clientFileName.getClientFileId())
                .toUriString();

        redirectAttributes.addFlashAttribute("uploaded", "File was successfully uploaded");

        return "redirect:/clients/" + name;
    }

    /**
     * Deletes the file and providing a success method if successful
     *
     * @param name               The name of the client passed as a parameter.
     * @param clientFileId       The client file ID to be used to delete the file.
     * @param redirectAttributes To send a success flash message on success.
     * @return Redirected to the same page of clients/{name}
     */
    @PostMapping("/clients/{name}/downloads/{clientFileId:.+}/delete")
    @Transactional
    public String deleteFile(@PathVariable String name, @PathVariable String clientFileId,
                             RedirectAttributes redirectAttributes) {

        clientFileService.deleteFile(clientFileId);
        redirectAttributes.addFlashAttribute("deleted", "File was successfully uploaded");
        return "redirect:/clients/" + name;
    }

    //@PostMapping("/multipleFiles")
    //public List<File> uploadingMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    //    return Arrays.asList(files).stream().map(file -> testingUpload("test", file)).collect(Collectors.toList());
    //}

}
