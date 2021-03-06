package com.danielmaia.businessmanagementsystem.UserInterfaceTests;


import com.danielmaia.businessmanagementsystem.MockHttpAndWebClient;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Login Page - UI Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 8080")
public class LoginPageTest {

    @LocalServerPort
    private int port;
    private String path = "/login";
    private MockHttpAndWebClient mock = new MockHttpAndWebClient();

    @Test
    @DisplayName("Page Loads Test")
    public void testLoginPageLoads() throws IOException {
        assertThat(mock.getWebResponseStatusCode(path, port)).isEqualTo(200);
    }

    @Test
    @DisplayName("Username Field and Title Appears Test")
    public void testUsernameAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "username", "name")).contains("username");
        assertThat(mock.getElemById(port, path,"username")).isEqualTo("input");
    }

    @Test
    @DisplayName("Password Field and Title Appears Test")
    public void testPasswordAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "password", "name")).contains("password");
        assertThat(mock.getElemByName(port, path, "password")).isEqualTo("input");
    }

    @Test
    @DisplayName("Test Login Button Appears")
    public void testLoginButtonAppears() throws IOException {
        assertThat(mock.getElemAndAttri(port, path, "login-btn", "name")).contains("login");
        assertThat(mock.getElemByName(port, path, "login-btn")).isEqualTo("button");
    }

    // Tests that the "lost your password" link appears
    @Test
    @DisplayName("Test Forgot Password Link Appears")
    public void testLostYourPasswordLinkAppears() throws IOException {
        assertThat(mock.getElemAndAttri(port, path, "lost-your-password", "name")).contains("lost-your-password");
        assertThat(mock.getElemByName(port, path, "lost-your-password")).isEqualTo("a");
    }

}
