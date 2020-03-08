package com.danielmaia.businessmanagementsystem.TestSuites;

import com.danielmaia.businessmanagementsystem.IntegrationTests.services.LoginServiceIntegrationTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Suite Tests")
@SelectClasses({LoginServiceIntegrationTest.class})
class SampleJUnitFiveTestSuite {}
