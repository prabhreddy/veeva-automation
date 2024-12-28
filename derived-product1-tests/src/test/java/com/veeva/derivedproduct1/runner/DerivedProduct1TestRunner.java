package com.veeva.derivedproduct1.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/main/resources/features/Tickets.feature",
        glue = {"com.veeva.derivedproduct1.steps", "com.veeva.framework.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/derived-product1-report.html"},
        monochrome = true
)
public class DerivedProduct1TestRunner extends AbstractTestNGCucumberTests {
}
