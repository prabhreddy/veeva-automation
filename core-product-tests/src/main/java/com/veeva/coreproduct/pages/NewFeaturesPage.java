package com.veeva.coreproduct.pages;

import com.veeva.framework.base.BasePage;
import com.veeva.framework.driver.DriverManager;
import com.veeva.framework.utils.Log;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NewFeaturesPage extends BasePage {

    private static final Logger logger = Log.getLogger(NewFeaturesPage.class);

    @FindBy(xpath = "//div[1]/div/div/div/div[2]/div/ul/li/div")
    private List<WebElement> videoFeeds;

    @FindBy(xpath = "//div[2]/div/div[2]/div/div/time")
    private List<WebElement> videoFeedDates;

    public NewFeaturesPage(WebDriver driver) {
        super(driver);
        logger.info("NewFeaturesPage initialized.");
    }

    public int getTotalVideoFeedsCount() throws InterruptedException {
        Thread.sleep(3000);
        int count = videoFeeds.size();
        logger.info("Total video feeds: {}", count);
        return count;
    }

    public int getRecentVideoFeedsCount(int days) {
        int count = 0;
        for (WebElement dateElement : videoFeedDates) {
            String dateText = dateElement.getText(); // Example: "3d", "5d", etc.
            if (dateText.endsWith("d")) {
                int postedDaysAgo = Integer.parseInt(dateText.replace("d", ""));
                if (postedDaysAgo >= days) {
                    count++;
                }
            }
        }
        logger.info("Video feeds posted more than {} days: {}", days, count);
        return count;
    }
}
