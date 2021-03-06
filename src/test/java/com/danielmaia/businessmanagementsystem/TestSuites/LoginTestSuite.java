package com.danielmaia.businessmanagementsystem.TestSuites;

import com.danielmaia.businessmanagementsystem.IntegrationTests.controllers.LoginControllerIT;
import com.danielmaia.businessmanagementsystem.UserInterfaceTests.LoginPageTest;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Suite Tests")
@SelectClasses({
        LoginPageTest.class,
        LoginControllerIT.class})
class LoginTestSuite {}
