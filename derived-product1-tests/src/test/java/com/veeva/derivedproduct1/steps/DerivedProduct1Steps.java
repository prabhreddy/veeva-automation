package com.veeva.derivedproduct1.steps;

import com.veeva.derivedproduct1.pages.TicketsPage;
import com.veeva.framework.config.ConfigLoader;
import com.veeva.framework.driver.DriverManager;
import com.veeva.framework.utils.ExtentReportManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.veeva.framework.utils.TestDataLoader;
import java.util.Collections;
import java.util.List;

import static com.veeva.framework.config.ConfigLoader.getInstance;

public class DerivedProduct1Steps {

    private WebDriver driver = DriverManager.getDriver();
    private TicketsPage ticketsPage;

    private static final Logger logger = LogManager.getLogger(DerivedProduct1Steps.class);

    public DerivedProduct1Steps() {
        ticketsPage = new TicketsPage(driver);
    }
    @Given("I navigate to the Derived Product 1 homepage")
    public void navigateToHomepage() {
        ticketsPage.navigateToHomePage();
        logger.info("Navigated to DP1 home page.");
        ExtentReportManager.getTest().pass("Navigated to DP1 home page successfully.");
    }

    @Then("I count the total number of slides")
    public void countSlides() throws InterruptedException {
        Thread.sleep(3000);
        int slideCount = ticketsPage.countSlides();
        Assert.assertTrue(slideCount > 0, "No slides found on the page.");
    }

    @Then("I validate the title of each slide with the expected data")
    public void validateSlideTitles() {
        List<String> actualSlideTitles = ticketsPage.getSlideTitles();  // Assume this method retrieves titles from the page
        List<String> expectedTitles = TestDataLoader.getStringList("dp1.slideTitles");

        logger.info("Actual Slide Titles: {}", actualSlideTitles);
        logger.info("Expected Slide Titles: {}", expectedTitles);

        Assert.assertEqualsNoOrder(actualSlideTitles.toArray(), expectedTitles.toArray(), "Slide titles do not match!");
    }



    @Then("I validate the duration of each slide with the expected duration")
    public void validateSlideDurations() {
        List<Integer> actualDurations = ticketsPage.getSlideDurations();
        logger.info("Actual Slide Durations: " + actualDurations);

        List<Integer> expectedDurations = getInstance().getIntListConfigValue("dp1.slideDurations");
        logger.info("Expected Slide Durations: " + expectedDurations);

        try {
            Assert.assertEquals(actualDurations, expectedDurations, "Slide durations do not match expected values.");
        } catch (AssertionError e) {
            logger.error("Slide durations assertion failed. Actual: " + actualDurations + ", Expected: " + expectedDurations);
            throw e;
        }
    }
}