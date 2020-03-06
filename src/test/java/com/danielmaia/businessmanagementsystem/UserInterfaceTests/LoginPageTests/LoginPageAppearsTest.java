package com.danielmaia.businessmanagementsystem.UserInterfaceTests.LoginPageTests;


import com.danielmaia.businessmanagementsystem.UserInterfaceTests.MockHtttpAndWebClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 80")
public class LoginPageAppearsTest {

    @LocalServerPort
    private int port;

    @Test
    public void testLoginPageLoads() throws IOException {
        MockHtttpAndWebClient mock = new MockHtttpAndWebClient();
        Assert.assertEquals("BMS | Login", mock.htmlPage("/login", port).getTitleText());
    }


}
