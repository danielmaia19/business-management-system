package com.danielmaia.businessmanagementsystem.UserInterfaceTests;

import com.danielmaia.businessmanagementsystem.MockHttpAndWebClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Forgot Password Page - UI Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server_port = 8080")
public class ResetPasswordTest {

    @LocalServerPort
    private int port;
    private String path = "/reset";
    private MockHttpAndWebClient mock = new MockHttpAndWebClient();

    @Test
    @DisplayName("Email Field Appears Test")
    public void testUsernameAppears() throws Exception {
        assertThat(mock.getElemAndAttri(port, path, "email", "name")).contains("email");
        assertThat(mock.getElemById(port, path,"email")).isEqualTo("input");
    }

}
