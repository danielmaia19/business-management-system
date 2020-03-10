package com.danielmaia.businessmanagementsystem.UnitTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.InvoicesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Invoices Controller - Unit Test")
class InvoicesControllerTest {

    @Autowired
    private InvoicesController controller;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Invoices Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/invoices"))
                .andExpect(status().isOk())
                .andExpect(view().name("invoices"));
    }
}