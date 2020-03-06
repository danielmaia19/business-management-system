package com.danielmaia.businessmanagementsystem.UserInterfaceTests.LoginPageTests;

import com.danielmaia.businessmanagementsystem.UserInterfaceTests.MockHtttpAndWebClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 80")
public class LoginFieldsAppearTest {

    @LocalServerPort
    private int port;

    private String path = "/login";
    private MockHtttpAndWebClient mock = new MockHtttpAndWebClient();

    @Test
    public void testUsernameAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("username").getAttribute("name").contains("username"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("username").getNodeName());
    }

    @Test
    public void testPasswordAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("password").getAttribute("name").contains("password"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getElementByName("password").getNodeName());
    }

    @Test
    public void testRememberMeCheckboxAppears() throws IOException {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("remember-me").getAttribute("name").contains("remember-me"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getElementByName("remember-me").getNodeName());
    }

    @Test
    public void testLoginButtonAppears() throws IOException {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("login-btn").getAttribute("name").contains("login"));
        Assert.assertEquals("button", mock.htmlPage(path, port).getElementByName("login-btn").getNodeName());
    }

    // Tests that the "lost your password" link appears
    @Test
    public void testLostYourPasswordLinkAppears() throws IOException {
        Assert.assertEquals(true, mock.htmlPage(path, port)
                .getElementByName("lost-your-password")
                .getAttribute("name").contains("lost-your-password"));
        Assert.assertEquals("a", mock.htmlPage(path, port).getElementByName("lost-your-password").getNodeName());
    }

}
