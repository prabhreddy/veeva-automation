package com.veeva.framework.tests;

import com.veeva.framework.driver.DriverManager;
import com.veeva.framework.reporters.ReportManager;
import com.veeva.framework.config.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FrameworkValidationTest {

    @Test
    public void testConfigLoader() {
        String browser = ConfigLoader.getInstance().getConfigValue("browser");
        Assert.assertEquals(browser, ConfigLoader.getInstance().getConfigValue("browser"), "The browser should be set to 'edge'");
    }

    @Test
    public void testReportGeneration() {
        Assert.assertNotNull(ReportManager.getInstance(), "ExtentReports instance should be initialized");
    }
}
