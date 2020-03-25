package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ClientsController;
import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ClientService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Clients Controller - Integration Test")
class ClientsControllerIT {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientsController controller;

    @Mock
    private Model model;

    @Autowired
    private MockMvc mvc;

    User user;
    Client client;
    private List<Client> clients = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        user = new User("Daniel", "Maia", "dmaia", "password", "dmaia@gmail.com");

        client = new Client("Client Name");

        clients.add(new Client("Client 1"));
        clients.add(new Client("Client 2"));
    }

    @Test
    @DisplayName("Client Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/clients").with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("name", "admin admin"))
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
    public void testViewClient() {
        String viewClient = controller.viewClient(clients.get(0).getName(), model);

        then(model).should().addAttribute(anyString(), any());
        assertThat(viewClient).isNotNull();
        assertThat("client/view").isEqualToIgnoringCase(viewClient);
    }




}