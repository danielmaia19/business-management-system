package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@DisplayName("Client Repository - Integration Test")
public class ClientRepositoryTest {

    @Mock
    private ClientRepository repository;

    private Client client;
    private List<Client> clients;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        client = new Client("Name");

        clients = new ArrayList<>();
        clients.add(new Client("Client 1"));
        clients.add(new Client("Client 2"));
    }

    @Test
    public void testFindByName() {
        when(repository.findByName("Name")).thenReturn(client);
        Client foundClient = repository.findByName("Name");

        assertThat(foundClient).isNotNull();
        assertThat(foundClient).isEqualTo(client);
    }

    @Test
    void testFindClientsByUser() {
        when(repository.findClientsByUser(user)).thenReturn(clients);
        List<Client> clientsFoundByUser = repository.findClientsByUser(user);

        assertThat(clientsFoundByUser).isNotNull();
        assertThat(clientsFoundByUser).containsAll(clients);

        verify(repository, atMostOnce()).findClientsByUser(any(User.class));
    }
}
