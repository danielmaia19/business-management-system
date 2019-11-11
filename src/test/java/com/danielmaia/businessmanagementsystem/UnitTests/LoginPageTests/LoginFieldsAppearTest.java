package com.danielmaia.businessmanagementsystem.UnitTests.LoginPageTests;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@SpringBootTest
public class LoginFieldsAppearTest {


    @Test
    void testUsernameAppears() throws Exception {
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("username").getAttribute("name").contains("username"));
        Assert.assertEquals("input", localPage.getHtmlElementById("username").getNodeName());
    }

    @Test
    void testPasswordAppears() throws Exception {
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("password").getAttribute("name").contains("password"));
        Assert.assertEquals("input", localPage.getElementByName("password").getNodeName());

    }

    @Test
    void testRememberMeCheckboxAppears() throws IOException {
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("remember-me").getAttribute("name").contains("remember-me"));
        Assert.assertEquals("input", localPage.getElementByName("remember-me").getNodeName());
    }

    @Test
    void testLoginButtonAppears() throws IOException {
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        final HtmlForm form = localPage.getFormByName("login-form");
        Assert.assertEquals(true, localPage.getElementByName("login-btn").getAttribute("name").contains("login"));
        Assert.assertEquals("button", localPage.getElementByName("login-btn").getNodeName());
    }

    // Tests that the "lost your password" link appears
    @Test
    void testLostYourPasswordLinkAppears() throws IOException {
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("lost-your-password").getAttribute("name").contains("lost-your-password"));
        Assert.assertEquals("a", localPage.getElementByName("lost-your-password").getNodeName());
    }

}
