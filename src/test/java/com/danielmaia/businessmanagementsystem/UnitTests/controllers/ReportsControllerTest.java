package com.danielmaia.businessmanagementsystem.UnitTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ReportsController;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Reports Controller - Unit Test")
class ReportsControllerTest {

    @Autowired
    private ReportsController controller;

    @Autowired
    private UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Reports Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reports").with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
                .andExpect(view().name("reports"));
    }
}