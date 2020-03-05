package com.danielmaia.businessmanagementsystem.TestSuites;

import com.danielmaia.businessmanagementsystem.IntegrationTests.controllers.SampleControllerIntegrationTest;
import com.danielmaia.businessmanagementsystem.IntegrationTests.data.SampleRepositoryIntegrationTests;
import com.danielmaia.businessmanagementsystem.UserInterfaceTests.LoginPageTests.LoginFieldsAppearTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginFieldsAppearTest.class, SampleControllerIntegrationTest.class, SampleRepositoryIntegrationTests.class})
public class SampleTestSuite extends Suite {
    public SampleTestSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }
}
