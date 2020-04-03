package com.danielmaia.businessmanagementsystem.UnitTests.services;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import com.danielmaia.businessmanagementsystem.Repository.ClientNoteRepository;
import com.danielmaia.businessmanagementsystem.Service.ClientNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientClientNoteServiceTest {

    @Mock
    private ClientNoteRepository repository;

    @InjectMocks
    private ClientNoteService service;

    private Client client;
    private ClientNote clientNote;
    List<ClientNote> clientNotes = new ArrayList<>();


    @BeforeEach
    void setUp() {
        client = new Client("Client");

        clientNote = new ClientNote("Some note here", client);

        clientNotes.add(new ClientNote("Note 1", client));
        clientNotes.add(new ClientNote("Note 2", client));

    }


    @Test
    public void testFindAllByClient() {
        when(repository.findAllByClient(client)).thenReturn(clientNotes);

        List<ClientNote> foundClientNotes = service.findAllByClient(client);

        assertThat(foundClientNotes).isNotNull();
        assertThat(foundClientNotes).isEqualTo(clientNotes);

    }

    @Test
    public void testFindNoteById() {
        when(repository.findNoteById(clientNote.getId())).thenReturn(clientNote);

        ClientNote foundClientNoteId = service.findNoteById(clientNote.getId());

        assertThat(foundClientNoteId).isNotNull();
        assertThat(foundClientNoteId).isEqualTo(clientNote);
    }

    @Test
    public void testDeleteNote() {
        service.deleteNote(clientNote);
        verify(repository).delete(any(ClientNote.class));
    }

    @Test
    public void testSaveNote() {
        service.saveNote(clientNote);
        verify(repository).save(any(ClientNote.class));
    }

}
