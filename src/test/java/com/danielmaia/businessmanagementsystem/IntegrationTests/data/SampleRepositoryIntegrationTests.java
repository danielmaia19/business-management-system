package com.danielmaia.businessmanagementsystem.IntegrationTests.data;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SampleRepositoryIntegrationTests {

    @Test
    public void myTest() {
        Assert.assertTrue(true);
    }

}
