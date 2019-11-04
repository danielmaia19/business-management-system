package com.danielmaia.businessmanagementsystem.RegisterPageTests;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootTest
class RegisterFieldsTest {

    @Test
    void registerFieldsAppear() throws Exception {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        try (final WebClient webClient = new WebClient()) {
            // Checks if the baseUrl is is either the staging or the live url, if its not then proceed with the localhost
            // with the port number as 8080 otherwise its live.
            if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
                final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
                final HtmlPage localPage = webClient.getPage(localBaseUrl + "/#signup");
                HtmlTextInput messageText = localPage.getHtmlElementById("reg-username");
                messageText.setValueAttribute("test");

            } else {
                final HtmlPage livePage = webClient.getPage(baseUrl + "/#signup");
                livePage.getHtmlElementById("register");
            }
        }
    }

    @Test
    void testUsernameRegisterFieldAppears() throws Exception {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        try (final WebClient webClient = new WebClient()) {
            // Checks if the baseUrl is is either the staging or the live url, if its not then proceed with the localhost
            // with the port number as 8080 otherwise its live.
            if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
                final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
                final HtmlPage localPage = webClient.getPage(localBaseUrl + "/#signup");
                localPage.getHtmlElementById("reg-username");
            } else {
                final HtmlPage livePage = webClient.getPage(baseUrl + "/#signup");
                livePage.getHtmlElementById("register");
            }
        }
    }

    @Test
    void testEmailRegisterFieldAppears() throws Exception {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        try (final WebClient webClient = new WebClient()) {
            // Checks if the baseUrl is is either the staging or the live url, if its not then proceed with the localhost
            // with the port number as 8080 otherwise its live.
            if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
                final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
                final HtmlPage localPage = webClient.getPage(localBaseUrl + "/#signup");
                localPage.getHtmlElementById("reg-email");
            } else {
                final HtmlPage livePage = webClient.getPage(baseUrl + "/#signup");
                livePage.getHtmlElementById("register");
            }
        }
    }

    @Test
    void testPasswordRegisterFieldAppears() throws Exception {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        try (final WebClient webClient = new WebClient()) {
            // Checks if the baseUrl is is either the staging or the live url, if its not then proceed with the localhost
            // with the port number as 8080 otherwise its live.
            if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
                final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
                final HtmlPage localPage = webClient.getPage(localBaseUrl + "/#signup");
                localPage.getHtmlElementById("reg-password");
            } else {
                final HtmlPage livePage = webClient.getPage(baseUrl + "/#signup");
                livePage.getHtmlElementById("register");
            }
        }
    }

    @Test
    void testConfirmPasswordRegisterFieldAppears() throws Exception {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        try (final WebClient webClient = new WebClient()) {
            // Checks if the baseUrl is is either the staging or the live url, if its not then proceed with the localhost
            // with the port number as 8080 otherwise its live.
            if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
                final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
                final HtmlPage localPage = webClient.getPage(localBaseUrl + "/#signup");
                localPage.getHtmlElementById("reg-confirmPassword");
            } else {
                final HtmlPage livePage = webClient.getPage(baseUrl + "/#signup");
                livePage.getHtmlElementById("register");
            }
        }
    }

    @Test
    void testSubmitRegisterButtonAppears() throws Exception {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        try (final WebClient webClient = new WebClient()) {
            // Checks if the baseUrl is is either the staging or the live url, if its not then proceed with the localhost
            // with the port number as 8080 otherwise its live.
            if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
                final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
                final HtmlPage localPage = webClient.getPage(localBaseUrl + "/#signup");
                localPage.getAnchorByHref("/dashboard");
            } else {
                final HtmlPage livePage = webClient.getPage(baseUrl + "/#signup");
                livePage.getAnchorByHref("/dashboard");
            }
        }
    }

    @Test
    void testAlreadyMemberLinkAppears() throws Exception {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        try (final WebClient webClient = new WebClient()) {
            // Checks if the baseUrl is is either the staging or the live url, if its not then proceed with the localhost
            // with the port number as 8080 otherwise its live.
            if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
                final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + ":8080";
                final HtmlPage localPage = webClient.getPage(localBaseUrl + "/#signup");
                localPage.getAnchorByHref("/dashboard");
            } else {
                final HtmlPage livePage = webClient.getPage(baseUrl + "/#signup");
                livePage.getAnchorByHref("/#signin");
            }
        }
    }

}
