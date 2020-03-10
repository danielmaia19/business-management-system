package com.danielmaia.businessmanagementsystem.Controller;

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
class ProjectsControllerTest {

    @Autowired
    private ProjectsController controller;

    @Autowired
    MockMvc mvc;

    @Test
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects"));
    }
}