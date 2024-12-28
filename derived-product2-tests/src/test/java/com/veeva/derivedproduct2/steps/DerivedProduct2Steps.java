package com.veeva.derivedproduct2.steps;

import com.veeva.framework.driver.DriverManager;
import com.veeva.framework.utils.CSVUtils;
import com.veeva.framework.utils.ExtentReportManager;
import com.veeva.derivedproduct2.pages.FooterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DerivedProduct2Steps {
    private static final Logger logger = LogManager.getLogger(DerivedProduct2Steps.class);
    private FooterPage footerPage;
    private WebDriver driver = DriverManager.getDriver();
    private List<String> footerLinks;
    private CSVUtils csvUtils = new CSVUtils();

    public DerivedProduct2Steps() {
        footerPage = new FooterPage(driver);
    }

    @Given("I navigate to the DP2 home page")
    public void navigateToDP2HomePage() {
        footerPage.navigateToHomePage();
        logger.info("Navigated to DP2 home page.");
        ExtentReportManager.getTest().pass("Navigated to DP2 home page successfully.");
    }

    @When("I scroll down to the footer")
    public void scrollToFooter() {
        footerPage.scrollToFooter();
        logger.info("Scrolled down to the footer.");
        ExtentReportManager.getTest().info("Scrolled down to the footer successfully.");
    }

    @Then("I fetch all footer hyperlinks into a CSV file")
    public void fetchFooterHyperlinks() throws IOException {
        footerLinks = footerPage.getFooterLinks();
        String footerLinksFileName = "footer-links.csv";
        createAndAttachFile(footerLinksFileName, footerLinks, "Footer Links");
    }

    private void createAndAttachFile(String fileName, List<String> content, String description) throws IOException {
        String filePath = "target/extent-reports/resources/" + fileName;
        csvUtils.writeToCSV(filePath, content);
        logger.info("{} saved to file: {}", description, filePath);

        // Use attachFileToReport method to add the file to the Extent Report
        attachFileToReport(fileName);
    }

    @And("I validate for any duplicate hyperlinks in the footer")
    public void validateDuplicateFooterLinks() throws IOException {
        Map<String, Integer> linkCountMap = new HashMap<>();
        List<String> duplicateLinks = new ArrayList<>();

        // Count the occurrence of each link
        for (String link : footerLinks) {
            linkCountMap.put(link, linkCountMap.getOrDefault(link, 0) + 1);
        }

        // Find duplicates
        for (Map.Entry<String, Integer> entry : linkCountMap.entrySet()) {
            if (entry.getValue() > 1) {
                duplicateLinks.add(entry.getKey());
            }
        }

        String duplicatesFileName = "duplicate-footer-links.csv";

        if (!duplicateLinks.isEmpty()) {
            createAndAttachFile(duplicatesFileName, duplicateLinks, "Duplicate Footer Links");
        } else {
            logger.info("No duplicate hyperlinks found in the footer.");
            ExtentReportManager.getTest().info("No duplicate hyperlinks found in the footer.");
        }
    }

    private void attachFileToReport(String fileName) {
        try {
            File file = new File("target/extent-reports/resources/" + fileName);
            if (file.exists() && file.length() > 0) {
                // Use relative path from the report location
                String relativePath = "resources/" + fileName;
                ExtentReportManager.getTest().info("Download " + fileName + ": <a href='" + relativePath + "'>" + fileName + "</a>");
                logger.info("Attached " + fileName + " successfully.");
            } else {
                logger.warn(fileName + " not found or is empty.");
                ExtentReportManager.getTest().warning(fileName + " not found or is empty.");
            }
        } catch (Exception e) {
            logger.error("Error attaching " + fileName, e);
            ExtentReportManager.getTest().fail("Error attaching " + fileName + ": " + e.getMessage());
        }
    }
}
