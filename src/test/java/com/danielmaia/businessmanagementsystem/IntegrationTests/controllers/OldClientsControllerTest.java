package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ClientsController;
import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OldClientsControllerTest {

    @Mock
    UserService userService;

    @Mock
    ClientService clientService;

    @Mock
    User user;

    @Mock
    Client client;

    @Mock
    Model model;

    @InjectMocks
    ClientsController controller;

    private List<Client> clients = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        client = new Client("Client Name");

        clients.add(new Client("Client 1"));
        clients.add(new Client("Client 2"));
        //given(clientService.findClientsByUser(user)).willReturn(clients);
    }

    @Test
    public void testViewClient() {
        // when
        String viewClient = controller.viewClient(clients.get(0).getName(), model);

        //then
        then(clientService).should().findByName(clients.get(0).getName());
        then(model).should().addAttribute(anyString(), any());
        assertThat(viewClient).isNotNull();
        assertThat("client/view").isEqualToIgnoringCase(viewClient);
    }

    @Test
    //TODO: Need to create this test.
    public void testCreateClient() {
        //String createClient = controller.createClient(client, , null);


        assertThat(true);
    }

}
