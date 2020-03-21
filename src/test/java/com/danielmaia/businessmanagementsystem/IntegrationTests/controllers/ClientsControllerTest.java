package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ClientsController;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Client Controller - Unit Test")
class ClientsControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Client Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/clients").with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
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

}