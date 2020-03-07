package com.danielmaia.businessmanagementsystem.UserInterfaceTests;

import com.danielmaia.businessmanagementsystem.MockHtttpAndWebClient;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 80")
public class RegisterUserPageTests {

    @LocalServerPort
    private int port;
    private String path = "/signup";
    private MockHtttpAndWebClient mock = new MockHtttpAndWebClient();

    @Test
    @DisplayName("Test Register User Page Loads")
    public void testRegisterPageLoads() throws IOException {
        assertEquals(200, mock.htmlPage(path, port).getWebResponse().getStatusCode());
    }

    @Test
    @DisplayName("Test First Name Label and Field Appears")
    public void testFirstNameFieldAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("first_name").getAttribute("name").contains("first_name"));
        assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("first_name").getNodeName());
    }

    @Test
    @DisplayName("Test Last Name Label and Field Appears")
    public void testLastNameAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("last_name").getAttribute("name").contains("last_name"));
        assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("last_name").getNodeName());
    }

    @Test
    @DisplayName("Test Username Label and Field Appears")
    public void testUsernameAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("username").getAttribute("name").contains("username"));
        assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("username").getNodeName());
    }

    @Test
    @DisplayName("Test Email Label and Field Appears")
    public void testEmailAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("email").getAttribute("name").contains("email"));
        assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("email").getNodeName());
    }

    @Test
    @DisplayName("Test Password Label and Field Appears")
    public void testPasswordAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("password").getAttribute("name").contains("password"));
        assertEquals("input", mock.htmlPage(path, port).getElementByName("password").getNodeName());
    }

    @Test
    @DisplayName("Test Password Confirmation Label and Field Appears")
    public void testConfirmPasswordFieldAppears() throws Exception {
        assertTrue(mock.htmlPage(path, port).getElementByName("confirm_password").getAttribute("name").contains("confirm_password"));
        assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("confirm_password").getNodeName());
    }

    // Checks the server which is being used, either the localhost or the live domain servers
    // Then checks whether the fields are shown.
    //public void checkUrlAndField(String field) throws IOException {
    //    MockHttpServletRequest request = new MockHttpServletRequest();
    //    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    //    final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
    //    final WebClient webClient = new WebClient();
    //
    //    if(baseUrl != "stage-business-ms.herokuapp.com:80" && baseUrl != "business-ms.herokuapp.com:80") {
    //        final String localBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + port;
    //        final HtmlPage localPage = webClient.getPage(localBaseUrl + "/signup");
    //        localPage.getElementsByName(field);
    //    } else {
    //        final HtmlPage livePage = webClient.getPage(baseUrl + "/signup");
    //        livePage.getElementsByName("username");
    //    }
    //
    //}

    //public void mock() {
    //    MockHttpServletRequest request = new MockHttpServletRequest();
    //    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    //}

}

