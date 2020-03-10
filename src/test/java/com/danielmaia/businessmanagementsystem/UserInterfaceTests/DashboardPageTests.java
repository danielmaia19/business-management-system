package com.danielmaia.businessmanagementsystem.UserInterfaceTests;

import com.danielmaia.businessmanagementsystem.MockHttpAndWebClient;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dashboard Page Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 8080")
public class DashboardPageTests {

    @LocalServerPort
    private int port;
    private String path = "/dashboard";
    private MockHttpAndWebClient mock = new MockHttpAndWebClient();

    @Test
    @DisplayName("Test Dashboard Page Loads")
    public void testDashboardPageLoads() throws IOException {
        assertThat(mock.getWebResponseStatusCode(path, port)).isEqualTo(200);
    }

}
