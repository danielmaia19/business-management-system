package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ClientsController;
import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Note;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.NoteService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Clients Controller - Integration Test")
class ClientsControllerIT {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientsController controller;

    @Autowired
    private ClientService clientService;

    @Autowired
    private NoteService noteService;

    @Mock
    private Model model;

    @Autowired
    private MockMvc mvc;

    private User user;
    private Client client;
    private List<Client> clients = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        client = new Client("Client Name", user);

        clients.add(new Client("Client 1", user));
        clients.add(new Client("Client 2", user));
    }

    @Test
    @DisplayName("Client Page View OK?")
    void index() throws Exception {
        clientService.saveAllClients(clients);

        User adminUser = userService.findByUsername("admin");

        mvc.perform(MockMvcRequestBuilders.get("/clients").with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("name", "Admin Admin"))
                .andExpect(MockMvcResultMatchers.model().attribute("clients", clientService.findClientsByUser(adminUser)))
                .andExpect(view().name("clients"));
    }

    @Test
    @DisplayName("User Can Create Client")
    public void testUserCanCreatClient() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/clients")
                .with(user(userService.loadUserByUsername("admin")))
                .param("name", "name")
                .param("addressLineOne", "address line 1")
                .param("addressLinesTwo", "address line 2")
                .param("city", "city")
                .param("region", "region")
                .param("postCode", "postCode")
                .param("country", "country"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clients"));
    }

    @Test
    @DisplayName("User Can View Client Page")
    public void testViewClientsAndNotes() throws Exception {
        clientService.saveClient(client);
        String viewClient = controller.viewClientsAndNotes(client.getName(), new Note("Some Note", client), model);

        mvc.perform(MockMvcRequestBuilders.get("/clients/{name}", client.getName()).with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("client", client))
                .andExpect(MockMvcResultMatchers.model().attribute("notes", noteService.findAllByClientOrderBySubmittedDateDesc(client)));

        assertThat(viewClient).isNotNull();
        assertThat("client/view").isEqualToIgnoringCase(viewClient);
    }



}