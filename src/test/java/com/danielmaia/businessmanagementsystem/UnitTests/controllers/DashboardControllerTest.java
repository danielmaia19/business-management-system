package com.danielmaia.businessmanagementsystem.UnitTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.DashboardController;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dashboard Controller Unit Test")
class DashboardControllerTest {

    @Autowired
    DashboardController controller;


    @Autowired
    private UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser
    @DisplayName("Dashboard Page View OK?")
    void dashboard() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/dashboard").with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("name", "Admin" + " Admin"))
                .andExpect(view().name("dashboard"));
    }
}