package com.danielmaia.businessmanagementsystem.UnitTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.DashboardController;
import com.danielmaia.businessmanagementsystem.DAO.UserDao;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dashboard Controller Unit Test")
class DashboardControllerTest {

    @Autowired
    DashboardController controller;

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser
    @DisplayName("Dashboard Page View OK?")
    void dashboard() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("name", nullValue()))
                .andExpect(redirectedUrl("/dashboard"));
    }
}