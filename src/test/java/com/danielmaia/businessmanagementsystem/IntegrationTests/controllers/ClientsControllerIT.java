package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ClientsController;
import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.ClientNoteService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private ClientNoteService clientNoteService;

    @Mock
    private Model model;

    @Autowired
    private MockMvc mvc;

    private Client client;
    private List<Client> clients = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        client = new Client("Client Name", (User) userService.loadUserByUsername("admin"));

        clients.add(new Client("Client 1", userService.findByUsername("admin")));
        clients.add(new Client("Client 2", userService.findByUsername("admin")));

    }

    @Test
    @DisplayName("Client Page View OK?")
    void index() throws Exception {
        Map<Client, Boolean> clientsAndLogo = new HashMap<>();

        for(Client client : clientService.findAllByUser(userService.findByUsername("admin"))) {
            Path path = Paths.get("src/main/resources/static/logos/admin/" + client.getName());

            // Checks if the directory exists
            clientsAndLogo.put(client, Files.exists(path));
        }

        mvc.perform(MockMvcRequestBuilders.get("/clients").with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("clients", clientsAndLogo))
                .andExpect(MockMvcResultMatchers.model().attribute("name", ((User) userService.loadUserByUsername("admin")).getFullName()))
                .andExpect(view().name("clients"));
    }

    @Test
    @DisplayName("User Can Create Client")
    public void testUserCanCreatClient() throws Exception {
        MockMultipartFile image = new MockMultipartFile("imageFile", "", "image/png", "{\"image\": src/main/resources/static/logos/logo.png}".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/clients").file(image)
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
    public void testViewClientAndNotes() throws Exception {
        clientService.saveClient(client);
        mvc.perform(MockMvcRequestBuilders.get("/clients/{name}", client.getName()).with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("client", client))
                .andExpect(MockMvcResultMatchers.model().attribute("notes", clientNoteService.findAllByClientOrderBySubmittedDateDesc(client)));
    }

}