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
public class RegisterUserPageAppearsTest {

    @Test
    public void testRegisterPageLoads() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/signup");
        Assert.assertEquals("BMS | Create User", localPage.getTitleText());
    }

}
