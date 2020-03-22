package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ClientsController;
import com.danielmaia.businessmanagementsystem.Controller.SignupController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Signup Controller - Integration Test")
class SignupControllerIT {

    @Autowired
    MockMvc mvc;

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("Signup Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    public void userCanRegisterAnAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/signup")
                .param("first_name", "dfgdfgdfgdfgd")
                .param("last_name", "Maidfgdfgdfgdfga")
                .param("username", "boo")
                .param("email", "boo@gmail.com")
                .param("password", "passworddfgdfgdfgsss")
        )
        .andExpect(model().errorCount(0))
        .andExpect(status().is3xxRedirection());
    }

}