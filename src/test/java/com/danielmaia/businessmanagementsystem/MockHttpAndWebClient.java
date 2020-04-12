package com.danielmaia.businessmanagementsystem;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 8080")
public class MockHttpAndWebClient {

    public HtmlPage getHtmlPage(String uriPath, int port) throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri().toURL().toString();
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        return webClient.getPage(baseUrl + ":" + port + uriPath);
    }

    /**
     * Gets the status code from the web response.
     * @param path
     * @param port
     * @return The status code from the web response
     * @throws IOException
     */
    public int getWebResponseStatusCode(String path, int port) throws IOException {
        return getHtmlPage(path, port).getWebResponse().getStatusCode();
    }

    /**
     * Is used to get the element by name and attribute
     * @param port
     * @param uriPath
     * @param element
     * @param attribute
     * @return A string which gets the element and attribute
     * @throws IOException
     */
    public String getElemAndAttri(int port, String uriPath, String element, String attribute) throws IOException {
        return getHtmlPage(uriPath, port).getElementByName(element).getAttribute(attribute);
    }

    /**
     * Gets the element by ID
     * @param port
     * @param uriPath
     * @param element
     * @return element by ID
     * @throws IOException
     */
    public String getElemById(int port, String uriPath, String element) throws IOException {
        return getHtmlPage(uriPath, port).getHtmlElementById(element).getNodeName();
    }

    /**
     * Gets the element by name
     * @param port
     * @param uriPath
     * @param element
     * @return The element by name
     * @throws IOException
     */
    public String getElemByName(int port, String uriPath, String element) throws IOException {
        return getHtmlPage(uriPath, port).getElementByName(element).getNodeName();
    }

}
