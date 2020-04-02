package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Note;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class NotesController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private ClientService clientService;

    // Add note
    @PostMapping(path = "/clients/{name}/add")
    public String postNote(Model model, @ModelAttribute("note") @Valid Note note, BindingResult result, @PathVariable("name") String name) {

        if(result.hasErrors()) {
            return "redirect:/clients/{name}?error=Field cannot be empty";
        }

        LocalDate today = LocalDate.now();

        Client client = clientService.findByName(name);
        note.setClient(client);
        note.setSubmittedDate(today);
        noteService.saveNote(note);

        model.addAttribute("client", client);
        model.addAttribute("note", note);

        return "redirect:/clients/{name}";
    }

    // Delete note
    @PostMapping(path = "/clients/{name}/note/{id}/delete")
    public String deleteNote(Model model, @ModelAttribute("note") Note note, @PathVariable("name") String name, @PathVariable("id") Long id) {
        noteService.deleteNote(noteService.findNoteById(id));

        return "redirect:/clients/{name}";
    }

    // Edit note
    //TODO: Allow users to edit their notes
    @PostMapping(path = "/clients/{name}/note/{id}/edit")
    public String editNote(Model model, @ModelAttribute("note") Note note, @PathVariable("name") String name, @PathVariable("id") Long id) {
        return "redirect:/clients/{name}";
    }

}
