package com.danielmaia.businessmanagementsystem.UnitTests.services;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ClientRepository;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import org.assertj.core.api.AssertionsForClassTypes;
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
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;

    Client client;
    User user;
    List<Client> clients;

    @BeforeEach
    public void setup(){
        client = new Client("Client");

        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        clients = new ArrayList<>();
        clients.add(new Client("Client 1"));
        clients.add(new Client("Client 2"));
    }

    @Test
    public void testFindByName() {
        when(repository.findByName("Client")).thenReturn(client);

        Client foundClient = service.findByName("Client");

        assertThat(foundClient).isNotNull();
        assertThat(foundClient).isEqualTo(client);

        verify(repository, atMostOnce()).findByName("Client");
    }

    @Test
    public void testSaveClient() {
        service.saveClient(client);
        verify(repository).save(any(Client.class));
    }

    @Test
    public void testFindClientsByUser() {

        when(repository.findClientsByUser(user)).thenReturn(clients);

        List<Client> foundProjects = service.findClientsByUser(user);

        assertThat(foundProjects).isNotNull();
        assertThat(foundProjects).isEqualTo(clients);

        verify(repository).findClientsByUser(any(User.class));
    }


    @Test
    public void testSaveAllClients() {
        service.saveAllClients(clients);
        verify(repository).saveAll(anyList());
    }

    @Test
    public void testDeleteClient() {
        //TODO: Do this!
        service.deleteClient(client);
        verify(repository).delete(any(Client.class));
    }

}
