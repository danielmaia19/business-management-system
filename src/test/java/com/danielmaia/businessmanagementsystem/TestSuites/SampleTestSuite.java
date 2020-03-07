package com.danielmaia.businessmanagementsystem.TestSuites;

import com.danielmaia.businessmanagementsystem.IntegrationTests.data.LoginRepositoryIntegrationTests;
import com.danielmaia.businessmanagementsystem.IntegrationTests.services.LoginServiceIntegrationTest;
import com.danielmaia.businessmanagementsystem.UserInterfaceTests.LoginPageTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginPageTests.class,
        LoginRepositoryIntegrationTests.class,
        LoginServiceIntegrationTest.class})
public class SampleTestSuite extends Suite {
    public SampleTestSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }
}
