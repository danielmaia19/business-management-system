package com.danielmaia.businessmanagementsystem.UserInterfaceTests.LoginPageTests;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@SpringBootTest
public class LoginFieldsAppearTest {

    @Test
    public void testUsernameAppears() throws Exception {
        mock();
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("username").getAttribute("name").contains("username"));
        Assert.assertEquals("input", localPage.getHtmlElementById("username").getNodeName());
    }

    @Test
    public void testPasswordAppears() throws Exception {
        mock();
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("password").getAttribute("name").contains("password"));
        Assert.assertEquals("input", localPage.getElementByName("password").getNodeName());

    }

    @Test
    public void testRememberMeCheckboxAppears() throws IOException {
        mock();
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("remember-me").getAttribute("name").contains("remember-me"));
        Assert.assertEquals("input", localPage.getElementByName("remember-me").getNodeName());
    }

    @Test
    public void testLoginButtonAppears() throws IOException {
        mock();
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        final HtmlForm form = localPage.getFormByName("login-form");
        Assert.assertEquals(true, localPage.getElementByName("login-btn").getAttribute("name").contains("login"));
        Assert.assertEquals("button", localPage.getElementByName("login-btn").getNodeName());
    }

    // Tests that the "lost your password" link appears
    @Test
    public void testLostYourPasswordLinkAppears() throws IOException {
        mock();
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals(true, localPage.getElementByName("lost-your-password").getAttribute("name").contains("lost-your-password"));
        Assert.assertEquals("a", localPage.getElementByName("lost-your-password").getNodeName());
    }

    public void mock() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

}
