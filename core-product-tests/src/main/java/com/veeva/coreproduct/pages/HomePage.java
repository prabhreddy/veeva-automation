package com.veeva.coreproduct.pages;

import com.veeva.framework.base.BasePage;
import com.veeva.framework.utils.ExtentReportManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = "//li/a/span[contains(text(),'Shop')]")
    private WebElement shopMenu;

    @FindBy(xpath = "//div[@class='p-2 absolute right-3 hover:cursor-pointer']")
    private WebElement frameClose;

    @FindBy(xpath = "//a[@tabindex='-1']/span[contains(text(),'...')]")
    private WebElement menuItem;

    @FindBy(xpath = "//a[contains(text(), 'News & Features')]")
    //li/a[@title='News & Features']
    private WebElement newFeaturesMenu;

    @FindBy(xpath = "//div[@class='p-2 absolute right-3 hover:cursor-pointer']")
    private WebElement alertCloseButton;

    public HomePage(WebDriver driver) {
        super(driver);
        logger.info("HomePage initialized.");
    }


    public void highlightElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid red'", element);
            Thread.sleep(500); // Pause for visibility
            js.executeScript("arguments[0].style.border=''", element); // Remove the highlight
        } catch (Exception e) {
            System.err.println("Failed to highlight element: " + e.getMessage());
        }
    }

    public void closeHomePageAlerts() {
        try {
            if (alertCloseButton.isDisplayed()) {
                alertCloseButton.click();
                logger.info("Closed an alert on the home page.");
                ExtentReportManager.getTest().info("Closed an alert on the home page.");
            }
        } catch (Exception e) {
            logger.warn("No alert found to close on the home page.");
        }
    }

    public void clickShopMenu() {
        shopMenu.click();
        logger.info("Clicked on the Shop menu.");
    }

    public void clickOnHomePageAlertClose() {
        frameClose.click();
        logger.info("Clicked on the alert.");
    }

    public void hoverOnMenuItem() {
        highlightElement(menuItem);
        Actions actions = new Actions(driver);
        actions.moveToElement(menuItem).perform();
        logger.info("Hovered on menu item.");
    }

    public void clickOnNewFeaturesMenu() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", newFeaturesMenu);

        logger.info("Clicked on New & Features menu.");
    }
}
