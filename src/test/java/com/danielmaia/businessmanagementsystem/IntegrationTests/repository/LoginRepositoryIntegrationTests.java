package com.danielmaia.businessmanagementsystem.IntegrationTests.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@DataJpaTest
@DisplayName("Login Repository - Integration Tests")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class LoginRepositoryIntegrationTests {

    @Test
    @DisplayName("😁 -> This is pretty cool" )
    public void myTest() {
        assertTrue(true);
    }

}
