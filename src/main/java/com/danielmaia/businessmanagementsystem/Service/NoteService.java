package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Note;
import com.danielmaia.businessmanagementsystem.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAllByClient(Client client) {
        return noteRepository.findAllByClient(client);
    }

    public List<Note> findAllByClientOrderBySubmittedDateDesc(Client client) {
        return noteRepository.findAllByClientOrderBySubmittedDateDesc(client);
    }

    public Note findNoteById(Long id) {
        return noteRepository.findNoteById(id);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

}
