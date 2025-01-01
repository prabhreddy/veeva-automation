package com.veeva.coreproduct.pages;

import com.veeva.framework.base.BasePage;
import com.veeva.framework.config.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShopPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ShopPage.class);
    @FindBy(xpath = "//a[@data-trk-id='topnav-group-ga-1_men']")
    private WebElement mensSection;

    @FindBy(xpath = "//span[normalize-space()='Jackets']")
    private WebElement jacketRadioButton;

    @FindBy(xpath = "//div[@class='product-card row']")
    private List<WebElement> jackets;

    @FindBy(xpath = "//div[@class='product-card-title']")
    private WebElement jacketTitleElement;

    @FindBy(xpath = "//div[@class='price-card']")
    private WebElement jacketPriceElement;

    @FindBy(xpath = "//div[@class='product-vibrancy-container']")
    private WebElement topSellerLabelElement;

    @FindBy(xpath = "//div[@class='pagination-component']//li[@class='next-page']//i[@role='presentation']")
    private WebElement nextPageButton;

    @FindBy(xpath = "//li/a/span[contains(text(),'Shop')]")
    private WebElement shopMenu;

    private final List<String> allJacketDetails = new ArrayList<>();

    public ShopPage(WebDriver driver) {
        super(driver);
        logger.info("ShopPage initialized.");
    }

    public void clickShopMenu() {
        shopMenu.click();
        logger.info("Clicked on the Shop menu.");
    }
    public void clickJacketRadioButton() {
        jacketRadioButton.click();
        logger.info("Clicked on the Jacket radio button.");
    }

    public void navigateToMensSection() throws InterruptedException {
        Thread.sleep(10000); // Replace with appropriate explicit wait later
        mensSection.click();
        logger.info("Navigated to Men's Jackets section.");
    }

    public List<String> getAllJacketDetails() throws InterruptedException {
        allJacketDetails.clear();
        boolean isFirstPage = true;
        do {
            collectCurrentPageJacketDetails(isFirstPage);
            isFirstPage = false;

        } while (navigateToNextPage());
        logger.info("Total jackets collected: {}", allJacketDetails.size());
        return allJacketDetails;
    }

    private void collectCurrentPageJacketDetails(boolean isFirstPage) throws InterruptedException {

        try {
            for (WebElement jacket : jackets) {
                try {
                    String title = jacketTitleElement.getText();
                    String price = jacketPriceElement.getText();
                    String topSeller;

                    if (isFirstPage) {
                        topSeller = (topSellerLabelElement != null && topSellerLabelElement.isDisplayed())
                                ? topSellerLabelElement.getText()
                                : "No Top Seller Label";
                    } else {
                        topSeller = "No Top Seller Label"; // Default for subsequent pages
                    }

                    allJacketDetails.add(String.format("Title: %s, Price: %s, Top Seller: %s", title, price, topSeller));

                } catch (Exception e) {
                    logger.warn("Failed to retrieve details for a jacket: {}", e.getMessage());
                }
            }
            logger.info("Collected details for {} jackets on the current page.", jackets.size());
        } catch (Exception e) {
            logger.error("Error while collecting jacket details: {}", e.getMessage());
        }


    }

    private boolean navigateToNextPage() {
        try {
            if (nextPageButton.isDisplayed()) {
                nextPageButton.click();
                logger.info("Navigated to the next page.");
                Thread.sleep(5000); // Wait for the next page to load. Replace with explicit wait later.
                return true;
            }
        } catch (Exception e) {
            logger.info("No more pages to navigate.");
        }
        return false;
    }
}
