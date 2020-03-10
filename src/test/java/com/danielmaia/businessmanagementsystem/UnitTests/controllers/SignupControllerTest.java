package com.danielmaia.businessmanagementsystem.UnitTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ClientsController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Signup Controller - Unit Test")
class SignupControllerTest {

    @Autowired
    private ClientsController controller;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Signup Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("settings"));
    }
}