package com.veeva.derivedproduct2.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/main/resources/features/Footer.feature",
        glue = {"com.veeva.derivedproduct2.steps", "com.veeva.framework.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/derived-product2-report.html"},
        monochrome = true
)
public class DerivedProduct2TestRunner extends AbstractTestNGCucumberTests {
}
