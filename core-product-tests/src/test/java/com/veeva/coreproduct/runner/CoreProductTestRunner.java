package com.veeva.coreproduct.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"com.veeva.coreproduct.steps", "com.veeva.framework.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/CoreProductTestReport.html"}
)
public class CoreProductTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}