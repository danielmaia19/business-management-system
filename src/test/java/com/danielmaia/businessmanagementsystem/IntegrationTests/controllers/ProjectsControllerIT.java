package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ProjectsController;
import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import com.danielmaia.businessmanagementsystem.Service.ProjectService;
import com.danielmaia.businessmanagementsystem.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Projects Controller - Integration Test")
class ProjectsControllerIT {

    @Autowired
    private ProjectsController controller;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Mock
    private Model model;

    private Project project;
    private Client client;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    public void setup() {
        client = new Client("Client Name", (User) userService.loadUserByUsername("admin"));
        project = new Project("Project Name", client);
    }

    @Test
    @DisplayName("Projects Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects").with(user(userService.loadUserByUsername("admin"))))
                .andExpect(status().isOk())
                .andExpect(view().name("projects"));
    }

    @Test
    @Disabled
    @DisplayName("User Can View Project Page")
    public void testViewProject() throws Exception {
        //projectService.saveProject(project);
        //String viewProject = controller.viewProject(client.getName(), model);
        //
        //mvc.perform(MockMvcRequestBuilders.get("/projects/{name}", project.getName()).with(user(userService.loadUserByUsername("admin"))))
        //        .andExpect(status().isOk())
        //        .andExpect(MockMvcResultMatchers.model().attribute("project", project));
        //
        //assertThat(viewProject).isNotNull();
        //assertThat("project/view").isEqualToIgnoringCase(viewProject);
    }

}