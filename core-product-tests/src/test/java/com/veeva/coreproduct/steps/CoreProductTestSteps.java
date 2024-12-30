package com.veeva.coreproduct.steps;

import com.aventstack.extentreports.Status;
import com.veeva.coreproduct.pages.HomePage;
import com.veeva.coreproduct.pages.NewFeaturesPage;
import com.veeva.coreproduct.pages.ShopPage;
import com.veeva.framework.driver.DriverManager;
import com.veeva.framework.utils.ExtentReportManager;
import com.veeva.framework.config.ConfigLoader;
import org.apache.logging.log4j.Logger;
import com.veeva.framework.utils.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

public class CoreProductTestSteps {

    private static final Logger logger = Log.getLogger(CoreProductTestSteps.class);
    private WebDriver driver = DriverManager.getDriver();
    private HomePage homePage;
    private ShopPage shopPage;
    private NewFeaturesPage newFeaturesPage;
    private String filePath;

    public CoreProductTestSteps() {
        shopPage = new ShopPage(driver); // Initialization through BasePage
        homePage = new HomePage(driver); // Initialization through BasePage
        newFeaturesPage = new NewFeaturesPage(driver);
    }

    @Given("I navigate to the Core Product home page")
    public void navigateToCoreProductHomePage() {
        // Fetch the Core Product URL from the configuration
        String coreProductUrl = ConfigLoader.getInstance().getConfigValue("urls.core_product");

        // Navigate to the URL
        driver.get(coreProductUrl);

        // Log navigation information
        logger.info("Navigated to Core Product home page: {}", coreProductUrl);
        ExtentReportManager.getTest().info("Navigated to Core Product home page: " + coreProductUrl);

        // Handle any alerts or popups on the homepage
        homePage.closeHomePageAlerts();

        // Validate that the page loaded successfully
        String pageTitle = driver.getTitle();
        logger.info("Page title: {}", pageTitle);
        ExtentReportManager.getTest().pass("Page loaded successfully with title: " + pageTitle);
    }


    @Given("I navigate to the shop menu for men's jackets")
    public void navigateToMensJackets() throws InterruptedException {
        // Before clicking on the Shop Menu, store the current tab handle
        String currentTab = driver.getWindowHandle();
        homePage.clickShopMenu();

        // Wait for the new tab to open and switch to it
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentTab)) {
                driver.switchTo().window(handle);
                logger.info("Switched to new tab.");
                ExtentReportManager.getTest().info("Switched to new browser tab.");
                break;
            }
        }

        shopPage.navigateToMensSection();
        ExtentReportManager.getTest().pass("Navigated to Men's section.");

        shopPage.clickJacketRadioButton();
        ExtentReportManager.getTest().info("Filtered with Jackets from the menu in the Men's section.");
        ExtentReportManager.getTest().pass("Navigated successfully to Men's Jackets section.");
    }


    @When("I extract jacket details from all pages")
    public void extractJacketDetailsFromPages() throws InterruptedException {
        List<String> jacketDetails = shopPage.getAllJacketDetails();
        logger.info("Extracted jacket details successfully.");
        ExtentReportManager.getTest().pass("Extracted jacket details like price, title, and TopSeller details from all the pages using pagination.");

        // Save details to file
        filePath = saveDetailsToFile(jacketDetails);
        ExtentReportManager.getTest().info("Extracted jacket details saved to text file and added the file to the target folder.");
    }

    @Then("I store jacket details in a text file and attach it to the report")
    public void storeJacketDetailsToFile() {
        attachFileToReport(filePath);
        ExtentReportManager.getTest().pass("Jacket details file attached to the report successfully.");
    }

    private String saveDetailsToFile(List<String> jacketDetails) {
        String filePath = "target/jacketDetails.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String detail : jacketDetails) {
                writer.write(detail);
                writer.newLine();
            }
            logger.info("Jacket details successfully saved to file: " + filePath);
        } catch (IOException e) {
            logger.error("Error writing jacket details to file: " + filePath, e);
        }
        return filePath;
    }

    private void attachFileToReport(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) {
                // Define a relative path for the file in the report resources folder
                String resourcesDir = "target/extent-reports/resources/";
                new File(resourcesDir).mkdirs(); // Create directory if it doesn't exist
                String reportFilePath = resourcesDir + file.getName();
                Files.copy(file.toPath(), Path.of(reportFilePath), StandardCopyOption.REPLACE_EXISTING);

                // Add a clickable link in the Extent Report
                String fileLink = "./resources/" + file.getName();
                ExtentReportManager.getTest().info("<a href='" + fileLink + "' download>Download Jacket Details</a>");
                logger.info("Attached jacket details file to the report successfully.");
            } else {
                logger.error("File not found or is empty: " + filePath);
                ExtentReportManager.getTest().fail("Failed to attach file: File not found or is empty at " + filePath);
            }
        } catch (IOException e) {
            logger.error("Error attaching file to report: " + filePath, e);
            ExtentReportManager.getTest().fail("Error attaching file to report: " + e.getMessage());
        }
    }





        //Second TC
        @Given("I navigate to the New & Features section from the menu")
        public void navigateToNewAndFeaturesSection() {
        homePage.hoverOnMenuItem();
        homePage.clickOnNewFeaturesMenu();
        ExtentReportManager.getTest().info("Navigated to New & Features section.");
        logger.info("Navigated to New & Features section.");
        }

        @When("I count total video feeds and video feeds posted within 3 days")
        public void countVideoFeeds() throws InterruptedException {
        int totalVideoFeeds = newFeaturesPage.getTotalVideoFeedsCount();
        int recentVideoFeeds = newFeaturesPage.getRecentVideoFeedsCount(3);

        ExtentReportManager.getTest().info("Total video feeds: " + totalVideoFeeds);
        ExtentReportManager.getTest().info("Video feeds posted within 3 days: " + recentVideoFeeds);
        logger.info("Total video feeds: {}, Video feeds within 3 days: {}", totalVideoFeeds, recentVideoFeeds);
        }

        @Then("I log the video feed counts in the report")
        public void logVideoFeedsCounts() {
        ExtentReportManager.getTest().pass("Video feed counts logged successfully.");
        }


    }

