package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Sample Controller Integration Test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SampleControllerIntegrationTest {

    @Test
    @DisplayName("Just a Test")
    public void myTest() {
        assertThat(true).isTrue();
    }

}
