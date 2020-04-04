package com.danielmaia.businessmanagementsystem.UserInterfaceTests;

import com.danielmaia.businessmanagementsystem.MockHttpAndWebClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Forgot Password Page - UI Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 8080")
public class ForgotPasswordPageTest {

    @LocalServerPort
    private int port;
    private String path = "/forgot-password";
    private MockHttpAndWebClient mock = new MockHttpAndWebClient();

    @Test
    @DisplayName("Test Dashboard Page Loads")
    public void testForgotPasswordPageLoads() throws IOException {
        assertThat(mock.getWebResponseStatusCode(path, port)).isEqualTo(200);
    }

    @Test
    @DisplayName("Email Field Appears Test")
    public void testUsernameAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "email", "name")).contains("email");
        assertThat(mock.getElemById(port, path,"email")).isEqualTo("input");
    }

    @Test
    @DisplayName("Test Forgot Password Button Appears")
    public void testForgotPasswordButtonAppears() throws IOException {
        assertThat(mock.getElemAndAttri(port, path, "forgot-password-btn", "name")).contains("forgot-password-btn");
        assertThat(mock.getElemByName(port, path, "forgot-password-btn")).isEqualTo("a");
    }


}
