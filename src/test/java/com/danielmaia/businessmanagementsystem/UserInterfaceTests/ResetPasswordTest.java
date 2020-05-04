package com.danielmaia.businessmanagementsystem.UserInterfaceTests;

import com.danielmaia.businessmanagementsystem.MockHttpAndWebClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Reset Password Page - UI Test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server_port = 8080")
public class ResetPasswordTest {

    @LocalServerPort
    private int port;
    private String path = "/reset?token=150";
    private MockHttpAndWebClient mock = new MockHttpAndWebClient();

    @Autowired
    MockMvc mockMvc;

    //@Test
    //@Disabled
    //@DisplayName("Password Reset Redirect and Show Fields Test")
    //public void testTokenRequestParamRedirects() throws Exception {
    //
    //    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    //    requestParams.add("token", "150");
    //
    //    mockMvc.perform(MockMvcRequestBuilders.get("/reset").params(requestParams)).andExpect(status().is3xxRedirection());
    //
    //    assertThat(mock.getElemAndAttri(port, path, "password", "name")).contains("password");
    //    assertThat(mock.getElemById(port, path,"password")).isEqualTo("input");
    //}

}
