package com.danielmaia.businessmanagementsystem.UnitTests.LoginPageTests;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@SpringBootTest
public class LoginPageAppearsTest {

    @Test
    void testLoginPageLoads() throws IOException {
        WebClient webClient = new WebClient();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        final HtmlPage localPage = webClient.getPage(baseUrl + "/login");
        Assert.assertEquals("BMS | Login", localPage.getTitleText());
    }


}
