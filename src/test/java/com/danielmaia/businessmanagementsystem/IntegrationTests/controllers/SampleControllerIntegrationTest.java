package com.danielmaia.businessmanagementsystem.IntegrationTests.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleControllerIntegrationTest {

    @Test
    public void myTest() {
        Assert.assertTrue(true);
    }

}
