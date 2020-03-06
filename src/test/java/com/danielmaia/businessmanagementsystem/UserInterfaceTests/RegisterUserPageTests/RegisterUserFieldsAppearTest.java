package com.danielmaia.businessmanagementsystem.UserInterfaceTests.RegisterUserPageTests;

import com.danielmaia.businessmanagementsystem.UserInterfaceTests.MockHtttpAndWebClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 80")
public class RegisterUserFieldsAppearTest {

    @LocalServerPort
    private int port;
    private String path = "/signup";
    private MockHtttpAndWebClient mock = new MockHtttpAndWebClient();

    @Test
    public void testFirstNameFieldAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("first_name").getAttribute("name").contains("first_name"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("first_name").getNodeName());
    }

    @Test
    public void testLastNameAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("last_name").getAttribute("name").contains("last_name"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("last_name").getNodeName());
    }

    @Test
    public void testUsernameAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("username").getAttribute("name").contains("username"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("username").getNodeName());
    }

    @Test
    public void testEmailAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("email").getAttribute("name").contains("email"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("email").getNodeName());
    }

    @Test
    public void testPasswordAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("password").getAttribute("name").contains("password"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getElementByName("password").getNodeName());
    }

    @Test
    public void testConfirmPasswordFieldAppears() throws Exception {
        Assert.assertTrue(mock.htmlPage(path, port).getElementByName("confirm_password").getAttribute("name")
                .contains("confirm_password"));
        Assert.assertEquals("input", mock.htmlPage(path, port).getHtmlElementById("confirm_password").getNodeName());
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

