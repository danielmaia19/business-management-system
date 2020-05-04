package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import com.danielmaia.businessmanagementsystem.Controller.ForgotPasswordController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Forgot Password Controller - Integration Test")
public class ForgotPasswordControllerIT {

    @Autowired
    private ForgotPasswordController forgotPasswordController;

    @Autowired
    MockMvc mvc;


    @Test
    @DisplayName("Forgot Password Page View OK?")
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password"));
    }

    @Test
    @Disabled
    @DisplayName("User Can Reset Password")
    public void testUserCanRestPassword() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/forgot-password")
                .param("email", "admin@gmail.com")
        )
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection());
    }

}
