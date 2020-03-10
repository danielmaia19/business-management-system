package com.danielmaia.businessmanagementsystem.TestSuites;

import com.danielmaia.businessmanagementsystem.Controller.LoginController;
import com.danielmaia.businessmanagementsystem.IntegrationTests.data.LoginRepositoryIntegrationTests;
import com.danielmaia.businessmanagementsystem.IntegrationTests.services.LoginServiceIntegrationTest;
import com.danielmaia.businessmanagementsystem.UserInterfaceTests.LoginPageTests;
import com.danielmaia.businessmanagementsystem.UnitTests.controllers.*;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Suite Tests")
@SelectClasses({
        LoginPageTests.class,
        LoginServiceIntegrationTest.class,
        LoginRepositoryIntegrationTests.class,
        LoginControllerTest.class})
class LoginTestSuite {}
