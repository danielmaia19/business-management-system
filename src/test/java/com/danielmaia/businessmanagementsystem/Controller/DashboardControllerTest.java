package com.danielmaia.businessmanagementsystem.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DashboardControllerTest {

    @Autowired
    DashboardController controller;

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser
    void dashboard() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(redirectedUrl("/dashboard"));
    }
}