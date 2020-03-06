package com.danielmaia.businessmanagementsystem.UserInterfaceTests.RegisterUserPageTests;

import com.gargoylesoftware.htmlunit.WebClient;
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
public class RegisterUserFieldsAppearTest {

    @Test
    public void testFirstNameFieldAppears() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/signup");
        Assert.assertEquals(true, localPage.getElementByName("first_name").getAttribute("name").contains("first_name"));
        Assert.assertEquals("input", localPage.getHtmlElementById("first_name").getNodeName());
    }

    @Test
    public void testLastNameFieldAppears() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        checkUrlAndField("last_name");
    }

    @Test
    public void testUsernameFieldAppears() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        checkUrlAndField("username");
    }

    @Test
    public void testEmailFieldAppears() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        checkUrlAndField("email");
    }

    @Test
    public void testPageFieldAppears() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        checkUrlAndField("password");
    }

    @Test
    public void testConfirmPasswordFieldAppears() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        checkUrlAndField("confirm_password");
    }

    // Checks the server which is being used, either the localhost or the live domain servers
    // Then checks whether the fields are shown.
    public void checkUrlAndField(String field) throws IOException {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final WebClient webClient = new WebClient();

        if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
            final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":80";
            final HtmlPage localPage = webClient.getPage(localBaseUrl + "/signup");
            localPage.getElementsByName(field);
        } else {
            final HtmlPage livePage = webClient.getPage(baseUrl + "/signup");
            livePage.getElementsByName("username");
        }

    }

}

