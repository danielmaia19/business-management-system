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
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private HoursWorkedService hoursWorkedService;

    /**
     * Displays all the users projects
     * @param model To add all the projects and the users full name.
     * @param project To enable the form to be filled by the yser.
     * @param authentication to get the current logged in user.
     * @return The project view
     */
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

    /**
     * Displays the view to add project
     * @param model To add the clients to then show and the project
     * @param project All the project information filled by the user.
     * @param authentication To get the current logged in user.
     * @return The view for creating a project.
     */
    @GetMapping("/projects/add")
    public String addProjectView(ModelMap model, Project project, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        List<Client> clients = clientService.findAllByUser(currentUser);

        model.addAttribute("clients", clients);
        model.addAttribute("project", project);
        return "project/add";
    }

    /**
     * Processes the addition of a new project
     * @param client The client selected for the project from the form.
     * @param project The project information to be created.
     * @param bindingResult
     * @return Redirected to the main projects view.
     */
    @PostMapping(path = "/projects")
    public String createProject(@RequestParam String client, @ModelAttribute @Valid Project project, BindingResult bindingResult) {
        Client selectedClient = clientService.findByName(client);
        if(project.getQuotePrice() == null) project.setQuotePrice(new BigDecimal(0));

        project.setClient(selectedClient);
        project.setCreatedOn(new DateTime().toDate());

        HoursWorked hoursWorked = new HoursWorked();
        hoursWorked.setHours(project.getTimeSpent());
        hoursWorked.setProject(project);
        hoursWorked.setTimestamp(new DateTime().toDate());

        List<HoursWorked> addHoursWorked = new ArrayList<>();
        addHoursWorked.add(hoursWorked);

        project.setHoursWorked(addHoursWorked);

        projectService.saveProject(project);
        return "redirect:/projects";
    }

    /**
     * Displays individual project view
     * @param name To get the project name
     * @param note
     * @param model Used to add multiple attributes for the view.
     * @return displays the individual project view from template of projects/view
     */
    @GetMapping(path = "/projects/{name}")
    public String viewProject(@PathVariable String name, @ModelAttribute("note") ProjectNote note, Model model) {
        Project project = projectService.findByName(name);

        int count = 0;

        for (HoursWorked hoursWorked : project.getHoursWorked()) {
            count += hoursWorked.getHours();
        }

        int minute = (int) TimeUnit.HOURS.toMinutes(count);
        StringBuilder timeSpentString=new StringBuilder();

        int day=minute/1440;
        int rem=minute%1440;
        int hour=rem/60;
        int Minute=rem%60;

        if(day>0)
            timeSpentString.append(day+" day ");

        if(hour>0)
            timeSpentString.append(hour+" hour ");

        if(Minute>0)
            timeSpentString.append(Minute+" minute");

        model.addAttribute("project", project);
        model.addAttribute("timeSpent", timeSpentString);

        model.addAttribute("count", timeSpentString);

        model.addAttribute("quotedPrice", project.getQuotePrice());
        model.addAttribute("projectsFiles", projectFileService.findAllByProject(project));
        model.addAttribute("notes", projectNoteService.findAllByProjectOrderBySubmittedDateDesc(project));
        return "project/view";
    }

    /**
     * Displays the project edit view form.
     * @param name To get the projects name.
     * @param model To add multiple attributes for the view.
     * @param authentication To get the current authenticated user.
     * @return Displays the form for edit project.
     */
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

    /**
     * Processes the project edit form.
     * @param name To get the project name
     * @param project The project information to be updated.
     * @return Redirected to the projects/{name} page
     */
    @PostMapping("/projects/{name}/edit")
    public String projectEdit(@PathVariable String name, @ModelAttribute Project project) {
        Project foundProject = projectService.findByName(name);

        foundProject.setName(project.getName());
        foundProject.setStatus(project.getStatus());
        foundProject.setProgress(project.getProgress());
        foundProject.setDescription(project.getDescription());
        foundProject.setProjectManager(project.getProjectManager());
        foundProject.setContactPerson(project.getContactPerson());
        foundProject.setClient(project.getClient());
        foundProject.setExpectedCompletionDate(project.getExpectedCompletionDate());

        if (project.getQuotePrice() == null) {
            foundProject.setQuotePrice(new BigDecimal(0));
        } else {
            foundProject.setQuotePrice(project.getQuotePrice());
        }

        HoursWorked hoursWorked = new HoursWorked();

        if(project.getTimeSpent() != 0) {
            hoursWorked.setProject(foundProject);
            hoursWorked.setTimestamp(new DateTime().toDate());
            hoursWorked.setHours(project.getTimeSpent());
            hoursWorkedService.saveHoursWorked(hoursWorked);
        }

        projectService.saveProject(foundProject);

        return "redirect:/projects/"+name;
    }

    /**
     * Processes the deletion of a project
     * @param name To get the project name
     * @param redirectAttributes Used to add flash messages.
     * @return Displays the projects page
     */
    @PostMapping("/projects/{name}/delete")
    public String projectDelete(@PathVariable String name, RedirectAttributes redirectAttributes) {
        try {
            hoursWorkedService.deleteAllHoursWorked(projectService.findByName(name));
            projectService.deleteProjectByName(name);
            redirectAttributes.addFlashAttribute("successDeletion", "Successfully removed the project");
        } catch (Exception e) {
            System.out.println("there was an error");
            redirectAttributes.addFlashAttribute("failedDeletion", "Failed to remove the project");
        }
        return "redirect:/projects";
    }

    /**
     * Processes creating the note
     * @param model To add attributes to the view.
     * @param note Note data to be processed
     * @param result To pass any error messages to the view.
     * @param name To get the projects name
     * @return Displays the projects/{name} page
     */
    @PostMapping(path = "/projects/{name}/add")
    public String postNote(Model model, @ModelAttribute @Valid ProjectNote note, BindingResult result, @PathVariable String name) {

        if (result.hasErrors()) return "redirect:/projects/{name}?error=Field cannot be empty";

        java.time.LocalDate today = java.time.LocalDate.now();

        Project project = projectService.findByName(name);
        note.setProject(project);
        note.setSubmittedDate(today);
        projectNoteService.saveNote(note);

        model.addAttribute("client", project);
        model.addAttribute("note", note);

        return "redirect:/projects/{name}";
    }

    /**
     * Processes the deletion of the note
     * @param projectNote
     * @param id used to find the note by ID to delete
     * @return Redirected to the projects/{name} page
     */
    @PostMapping(path = "/projects/{name}/note/{id}/delete")
    public String deleteNote(@ModelAttribute("note") ProjectNote projectNote, @PathVariable Long id) {
        projectNoteService.deleteNote(projectNoteService.findNoteById(id));
        return "redirect:/projects/{name}";
    }

    /**
     * Processes for uploading files
     * @param name To get the project name.
     * @param file Gets the file to be processed.
     * @param redirectAttributes Used to send flash messages to the view.
     * @return Redirected to the projects{name} page.
     * @throws IOException
     */
    @PostMapping("/projects/{name}/upload")
    public String testingUpload(@PathVariable String name, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        Project project = projectService.findByName(name);
        ProjectFile projectFileName = projectFileService.saveFile(project, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/projects/" + name + "/downloads/")
                .path(projectFileName.getProjectFileId())
                .toUriString();

        redirectAttributes.addFlashAttribute("uploaded", "File was successfully uploaded");

        return "redirect:/projects/" + name;
    }

    /**
     * Process for deleting a file.
     * @param projectFileId To get the project file id to find and delete.
     * @param redirectAttributes To send flash messages to the view.
     * @return Redirected to the projects/{name} page
     */
    @PostMapping("/projects/{name}/downloads/{projectFileId:.+}/delete")
    public String deleteFile(@PathVariable String projectFileId, RedirectAttributes redirectAttributes) {
        projectFileService.deleteFile(projectFileId);
        redirectAttributes.addFlashAttribute("deleted", "File was successfully deleted");
        return "redirect:/projects/{name}";
    }

    /**
     * REST for showing the number of hours worked for the project.
     * @param name used to find the project by name
     * @return JSON string representation of the number of hours and months.
     */
    @ResponseBody
    @GetMapping("/projects/{name}/chart")
    public String lineChart(@PathVariable String name) {
        Project project = projectService.findByName(name);
        List<HoursWorked> hoursWorked = hoursWorkedService.findAllByProject(project);
        LinkedHashMap<String, Integer> prevTwelveMonths = new LinkedHashMap<>();

        JsonArray jsonMonth = new JsonArray();
        JsonArray jsonDaysWorkedCount = new JsonArray();
        JsonArray jsonHoursWorkedCount = new JsonArray();
        JsonObject json = new JsonObject();

        // Sorts the projects by date
        hoursWorked.sort(new Comparator<HoursWorked>() {

            @Override
            public int compare(HoursWorked o1, HoursWorked o2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                    return sdf.parse(o1.getTimestamp().toString()).compareTo(sdf.parse(o2.getTimestamp().toString()));  //sdf.parse returns date - So, Compare Date with date
                } catch (ParseException ex) {
                    return o1.getTimestamp().toString().compareTo(o2.getTimestamp().toString());
                }
            }
        });

        for(HoursWorked hoursWorked1 : hoursWorked) {
            Date hoursWorkedDate = hoursWorked1.getTimestamp();
            LocalDate date = new LocalDate(hoursWorkedDate);
            String month = date.monthOfYear().getAsText();

            if(prevTwelveMonths.size() < 12) {
                if(!prevTwelveMonths.containsKey(month)) {
                    prevTwelveMonths.put(month,hoursWorked1.getHours());
                } else {
                    prevTwelveMonths.replace(month, prevTwelveMonths.get(month)+hoursWorked1.getHours());
                }
            } else {
                // Removes the first month when the 12 month has been reached
                System.out.println(prevTwelveMonths.entrySet().toArray()[0]);
            }
        }

        prevTwelveMonths.forEach((key, value) -> {
            jsonMonth.add(key);

            // Convert hours to days
            int days = (int) TimeUnit.HOURS.toDays(value);
            jsonDaysWorkedCount.add(days);
            jsonHoursWorkedCount.add(value);
        });

        json.add("month", jsonMonth);
        json.add("days", jsonDaysWorkedCount);
        json.add("hours", jsonHoursWorkedCount);
        return json.toString();
    }

}


