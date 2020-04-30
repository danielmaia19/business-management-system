package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.*;
import com.danielmaia.businessmanagementsystem.Repository.ProjectNoteRepository;
import com.danielmaia.businessmanagementsystem.Service.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Transactional
public class ProjectsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectNoteService projectNoteService;

    @Autowired
    private ProjectFileService projectFileService;

    //Show all users projects
    @GetMapping("/projects")
    public String index(ModelMap model, Project project, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        List<Client> clients = clientService.findAllByUser(currentUser);
        List<Project> projects = new ArrayList<>();

        for (Client client : clients) {
            projects.addAll(client.getProjects());
        }

        model.addAttribute("projects", projects);
        model.addAttribute("name", currentUser.getFullName());
        return "projects";
    }

    // Show view to add projects
    @GetMapping("/projects/add")
    public String addProjectView(ModelMap model, Project project, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());

        List<Client> clients = clientService.findAllByUser(currentUser);

        model.addAttribute("clients", clients);
        model.addAttribute("project", project);

        return "project/add";
    }

    // Add new projects
    @PostMapping(path = "/projects")
    public String createClient(@RequestParam("client") String client, @ModelAttribute("project") @Valid Project project, BindingResult bindingResult, Authentication authentication) {
        Client selectedClient = clientService.findByName(client);

        System.out.println(selectedClient.getName());

        project.setCreatedOn(new Date());

        //project.setUser(currentUser);
        project.setClient(selectedClient);
        projectService.saveProject(project);

        return "redirect:/projects";
    }

    // Individual project view
    @GetMapping(path = "/projects/{name}")
    public String viewProject(@PathVariable("name") String name, @ModelAttribute("note") ProjectNote projectNote, Model model) {
        Project project = projectService.findByName(name);

        model.addAttribute("project", project);
        model.addAttribute("projectsFiles", projectFileService.findAllByProject(project));
        model.addAttribute("notes", projectNoteService.findAllByProjectOrderBySubmittedDateDesc(project));
        return "project/view";
    }

    @GetMapping("/projects/{name}/edit")
    public String projectEditView(@PathVariable String name, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
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
        foundProject.setProjectManager(project.getProjectManager());
        foundProject.setContactPerson(project.getContactPerson());
        foundProject.setQuotePrice(project.getQuotePrice());
        foundProject.setClient(project.getClient());
        foundProject.setTimeSpent(project.getTimeSpent());
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

    // Notes for Project

    // Add note
    @PostMapping(path = "/projects/{name}/add")
    public String postNote(Model model, @ModelAttribute("note") @Valid ProjectNote projectNote, BindingResult result, @PathVariable("name") String name) {

        if (result.hasErrors()) {
            return "redirect:/projects/{name}?error=Field cannot be empty";
        }

        java.time.LocalDate today = java.time.LocalDate.now();

        Project project = projectService.findByName(name);
        projectNote.setProject(project);
        projectNote.setSubmittedDate(today);
        projectNoteService.saveNote(projectNote);

        model.addAttribute("client", project);
        model.addAttribute("note", projectNote);

        return "redirect:/projects/{name}";
    }

    // Delete note
    @PostMapping(path = "/projects/{name}/note/{id}/delete")
    public String deleteNote(Model model, @ModelAttribute("note") ProjectNote projectNote, @PathVariable("name") String name, @PathVariable("id") Long id) {
        projectNoteService.deleteNote(projectNoteService.findNoteById(id));

        return "redirect:/projects/{name}";
    }

    // Edit note
    //TODO: Allow users to edit their notes
    //@PostMapping(path = "/clients/{name}/note/{id}/edit")
    //public String editNote(Model model, @ModelAttribute("note") ClientNote clientNote, @PathVariable("name") String name, @PathVariable("id") Long id) {
    //    return "redirect:/clients/{name}";
    //}

    // Files
    @PostMapping("/projects/{name}/upload")
    public String testingUpload(@PathVariable("name") String name, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        Project project = projectService.findByName(name);
        ProjectFile projectFileName = projectFileService.saveFile(project, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/projects/" + name + "/downloads/")
                .path(projectFileName.getProjectFileId())
                .toUriString();

        redirectAttributes.addFlashAttribute("uploaded", "File was successfully uploaded");

        return "redirect:/projects/" + name;
    }

    @PostMapping("/projects/{name}/downloads/{projectFileId:.+}/delete")
    public String deleteFile(@PathVariable String name, @PathVariable String projectFileId, RedirectAttributes redirectAttributes) {

        projectFileService.deleteFile(projectFileId);

        redirectAttributes.addFlashAttribute("deleted", "File was successfully deleted");

        return "redirect:/projects/" + name;
    }


    @GetMapping("/projects/{name}/chart")
    @ResponseBody
    public String lineChart(@PathVariable("name") String name) {
        Project project = projectService.findByName(name);
        //LinkedHashMap<String, Integer> prevTwelveMonths = new LinkedHashMap<>();
        NavigableMap<DateTime, Integer> prevTwelveMonths = new TreeMap<>();
        DateTime dateTime = DateTime.now();

        JsonArray jsonMonth = new JsonArray();
        JsonArray jsonProjectHours = new JsonArray();
        JsonObject json = new JsonObject();
        
        prevTwelveMonths.put(dateTime.minusMonths(1).monthOfYear().getDateTime(), project.getTimeSpent());
        prevTwelveMonths.put(dateTime.minusMonths(2).monthOfYear().getDateTime(), project.getTimeSpent());
        prevTwelveMonths.put(dateTime.minusMonths(3).monthOfYear().getDateTime(), project.getTimeSpent());
        prevTwelveMonths.put(dateTime.minusMonths(4).monthOfYear().getDateTime(), project.getTimeSpent());

        //System.out.println(dateTime.minusMonths(1).monthOfYear().getDateTime());

        for (Map.Entry<DateTime, Integer> date1 : prevTwelveMonths.entrySet()) {
            System.out.println("Sorted : " + new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH).format(date1));
        }


        //prevTwelveMonths.forEach((key, value) -> {
        //    jsonMonth.add(key);
        //    jsonProjectHours.add(value);
        //});

        json.add("month", jsonMonth);
        json.add("count", jsonProjectHours);
        return json.toString();
    }


}


