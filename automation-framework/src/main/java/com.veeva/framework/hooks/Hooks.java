package com.veeva.framework.hooks;

import com.veeva.framework.config.ConfigLoader;
import com.veeva.framework.driver.DriverManager;
import com.veeva.framework.utils.ExtentReportManager;
import com.veeva.framework.utils.Log;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private static final Logger logger = Log.getLogger(Hooks.class);
    private String moduleName;


    @Before
    public void setup(Scenario scenario) {
        synchronized (this) {
            String browser = ConfigLoader.getInstance().getConfigValue("browser");
            DriverManager.setDriver(browser); // Now resolved
        }
        String moduleName = getModuleNameFromScenario(scenario); // Extract moduleName from scenario or feature file
        if (moduleName == null || moduleName.trim().isEmpty()) {
            moduleName = "DefaultModule"; // Set a default module name
        }
        ExtentReportManager.createTest(moduleName, scenario.getName());
        logger.info("Starting Scenario: {} in module: {}", scenario.getName(), moduleName);
//        ExtentReportManager.createTest(getModuleNameFromScenario(scenario), scenario.getName());
//        logger.info("Starting Scenario: {} in module: {}", scenario.getName(), moduleName);
    }


    @After
    public void tearDown(Scenario scenario) {
        String moduleName = getModuleNameFromScenario(scenario);
        if (moduleName == null || moduleName.trim().isEmpty()) {
            moduleName = "DefaultModule"; // Use a default module name
        }
            if (scenario.isFailed()) {
                ExtentReportManager.getTest().fail("Scenario Failed: " + scenario.getName());
                captureScreenshot(scenario, "Failed Step Screenshot");
            } else {
                ExtentReportManager.getTest().pass("Scenario Passed: " + scenario.getName());
            }

        ExtentReportManager.flush(moduleName); // Flush Extent Report
        DriverManager.quitDriver(); // Clean up WebDriver
        logger.info("Completed Scenario: {}", scenario.getName());
    }

    private void captureScreenshot(Scenario scenario, String description) {
        try {
            final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", description);
            ExtentReportManager.getTest().addScreenCaptureFromBase64String(
                    ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64),
                    description);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    private String getModuleNameFromScenario(Scenario scenario) {
        String featureFileName = scenario.getUri().toString();
        if (featureFileName.contains("core-product")) {
            return "Core Product";
        } else if (featureFileName.contains("derived-product1")) {
            return "Derived Product 1";
        } else if (featureFileName.contains("derived-product2")) {
            return "Derived Product 2";
        }
        return "DefaultModule";
    }
}
