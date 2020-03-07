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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 80")
public class DashboardPageTests {

    @LocalServerPort
    private int port;
    private String path = "/dashboard";
    private MockHtttpAndWebClient mock = new MockHtttpAndWebClient();

    @Test
    @DisplayName("Test Dashboard Page Loads")
    public void testDashboardPageLoads() throws IOException {
        assertEquals(200, mock.htmlPage(path, port).getWebResponse().getStatusCode());
    }

}
