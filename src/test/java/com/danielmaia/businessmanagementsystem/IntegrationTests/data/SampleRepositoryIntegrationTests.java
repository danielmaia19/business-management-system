package com.danielmaia.businessmanagementsystem.IntegrationTests.data;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@RunWith(SpringRunner.class)
//@DataJpaTest
@DisplayName("Sample Repository Integration Tests")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SampleRepositoryIntegrationTests {

    @Test
    @DisplayName("My Test")
    public void myTest() {
        assertTrue(true);
    }

}
