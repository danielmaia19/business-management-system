package com.danielmaia.businessmanagementsystem.UserInterfaceTests;

import com.danielmaia.businessmanagementsystem.MockHttpAndWebClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Signup Page - UI Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 8080")
public class SignupPageTests {

    @LocalServerPort
    private int port;
    private String path = "/signup";
    private MockHttpAndWebClient mock = new MockHttpAndWebClient();

    @Test
    @DisplayName("Page Loads")
    public void testRegisterPageLoads() throws IOException {
        assertThat(mock.getWebResponseStatusCode(path,port)).isEqualTo(200);
    }

    @Test
    @DisplayName("First Name Label and Field Appears")
    public void testFirstNameFieldAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "first_name", "name")).contains("first_name");
        assertThat(mock.getElemById(port, path, "first_name")).isEqualTo("input");
    }

    @Test
    @DisplayName("Last Name Label and Field Appears")
    public void testLastNameAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "last_name", "name")).contains("last_name");
        assertThat(mock.getElemById(port, path, "last_name")).isEqualTo("input");
    }

    @Test
    @DisplayName("Username Label and Field Appears")
    public void testUsernameAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "username", "name")).contains("username");
        assertThat(mock.getElemById(port, path, "username")).isEqualTo("input");
    }

    @Test
    @DisplayName("Email Label and Field Appears")
    public void testEmailAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "email", "name")).contains("email");
        assertThat(mock.getElemById(port, path, "email")).isEqualTo("input");
    }

    @Test
    @DisplayName("Password Label and Field Appears")
    public void testPasswordAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "password", "name")).contains("password");
        assertThat(mock.getElemById(port, path, "password")).isEqualTo("input");
    }

}

