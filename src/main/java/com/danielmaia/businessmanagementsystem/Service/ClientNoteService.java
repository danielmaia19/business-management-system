package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import com.danielmaia.businessmanagementsystem.Repository.ClientNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientNoteService {

    @Autowired
    private ClientNoteRepository clientNoteRepository;

    public List<ClientNote> findAllByClient(Client client) {
        return clientNoteRepository.findAllByClient(client);
    }

    public List<ClientNote> findAllByClientOrderBySubmittedDateDesc(Client client) {
        return clientNoteRepository.findAllByClientOrderBySubmittedDateDesc(client);
    }

    public ClientNote findNoteById(Long id) {
        return clientNoteRepository.findNoteById(id);
    }

    public void deleteNote(ClientNote clientNote) {
        clientNoteRepository.delete(clientNote);
    }

    public void saveNote(ClientNote clientNote) {
        clientNoteRepository.save(clientNote);
    }

}
