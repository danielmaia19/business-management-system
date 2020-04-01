package com.danielmaia.businessmanagementsystem.UnitTests.services;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Note;
import com.danielmaia.businessmanagementsystem.Repository.NoteRepository;
import com.danielmaia.businessmanagementsystem.Service.NoteService;
import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private NoteService service;

    private Client client;
    private Note note;
    List<Note> notes = new ArrayList<>();


    @BeforeEach
    void setUp() {
        client = new Client("Client");

        note = new Note("Some note here", client);

        notes.add(new Note("Note 1", client));
        notes.add(new Note("Note 2", client));

    }


    @Test
    public void testFindAllByClient() {
        when(repository.findAllByClient(client)).thenReturn(notes);

        List<Note> foundNotes = service.findAllByClient(client);

        assertThat(foundNotes).isNotNull();
        assertThat(foundNotes).isEqualTo(notes);

    }

    @Test
    public void testFindNoteById() {
        when(repository.findNoteById(note.getId())).thenReturn(note);

        Note foundNoteId = service.findNoteById(note.getId());

        assertThat(foundNoteId).isNotNull();
        assertThat(foundNoteId).isEqualTo(note);
    }

    @Test
    public void testDeleteNote() {
        service.deleteNote(note);
        verify(repository).delete(any(Note.class));
    }

    @Test
    public void testSaveNote() {
        service.saveNote(note);
        verify(repository).save(any(Note.class));
    }

}
