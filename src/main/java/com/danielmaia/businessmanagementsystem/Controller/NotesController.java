package com.danielmaia.businessmanagementsystem.Controller;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.ClientNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class NotesController {

    @Autowired
    private ClientNoteService clientNoteService;

    @Autowired
    private ClientService clientService;

    // Add note
    @PostMapping(path = "/clients/{name}/add")
    public String postNote(Model model, @ModelAttribute("note") @Valid ClientNote clientNote, BindingResult result, @PathVariable("name") String name) {

        if(result.hasErrors()) {
            return "redirect:/clients/{name}?error=Field cannot be empty";
        }

        LocalDate today = LocalDate.now();

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

}
