package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("Client Repository - Integration Test")
public class ClientRepositoryIT {

    @Autowired
    private ClientRepository repository;

    private Client client;
    private List<Client> clients = new ArrayList<>();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        client = new Client("Name", user);

        clients.add(new Client("Client 1", user));
        clients.add(new Client("Client 2", user));
    }

    @Test
    public void testFindByName() {
        repository.save(client);

        Client foundClient = repository.findByName("Name");

        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getName()).isEqualTo("Name");
    }

    @Test
    void testFindClientsByUser() {
        repository.save(clients.get(0));
        repository.save(clients.get(1));

        List<Client> foundClients = repository.findClientsByUser(user);

        assertThat(foundClients).isNotNull();
        assertThat(foundClients).isEqualTo(clients);
    }
}
