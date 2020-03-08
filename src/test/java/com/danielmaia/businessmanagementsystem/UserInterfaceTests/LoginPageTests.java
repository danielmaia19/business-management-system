package com.danielmaia.businessmanagementsystem.UserInterfaceTests;


import com.danielmaia.businessmanagementsystem.MockHtttpAndWebClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@RunWith(SpringRunner.class)
@DisplayName("Login Page Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 80")
public class LoginPageTests {

    @LocalServerPort
    private int port;
    private String path = "/login";
    private MockHtttpAndWebClient mock = new MockHtttpAndWebClient();

    @Test
    @DisplayName("Test Page Loads With Correct Page Title ")
    public void testLoginPageLoads() throws IOException {
        assertEquals(200, mock.htmlPage(path, port).getWebResponse().getStatusCode());
    }

    @Test
    @DisplayName("Test Username Field and Title Appears")
    public void testUsernameAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("username").getAttribute("name").contains("username"));
        assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("username").getNodeName());
    }

    @Test
    @DisplayName("Test Password Field and Title Appears")
    public void testPasswordAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("password").getAttribute("name").contains("password"));
        assertEquals("input", mock.htmlPage(path, port).getElementByName("password").getNodeName());
    }

    @Test
    @DisplayName("Test Remember Me Checkbox and Title Appears")
    public void testRememberMeCheckboxAppears() throws IOException {
        assertTrue(mock.htmlPage(path, port).getElementByName("remember-me").getAttribute("name").contains("remember-me"));
        assertEquals("input", mock.htmlPage(path, port).getElementByName("remember-me").getNodeName());
    }

    @Test
    @DisplayName("Test Login Button Appears")
    public void testLoginButtonAppears() throws IOException {
        assertTrue(mock.htmlPage(path, port).getElementByName("login-btn").getAttribute("name").contains("login"));
        assertEquals("button", mock.htmlPage(path, port).getElementByName("login-btn").getNodeName());
    }

    // Tests that the "lost your password" link appears
    @Test
    @DisplayName("Test Forgot Password Link Appears")
    public void testLostYourPasswordLinkAppears() throws IOException {
        assertEquals(true, mock.htmlPage(path, port).getElementByName("lost-your-password").getAttribute("name")
                .contains("lost-your-password"));
        assertEquals("a", mock.htmlPage(path, port).getElementByName("lost-your-password").getNodeName());
    }

}
