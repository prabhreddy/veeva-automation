package com.veeva.derivedproduct1.pages;

import com.veeva.framework.base.BasePage;
import com.veeva.framework.config.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TicketsPage extends BasePage {
    String derived_product1Url = ConfigLoader.getInstance().getConfigValue("urls.derived_product1");
    private static final Logger logger = LoggerFactory.getLogger(TicketsPage.class);

    @FindBy(xpath = "//div[@role='tablist']/button[@type='button']")
    private List<WebElement> slides;

    @FindBy(xpath = "//button[@type='button']/div[@class='TileHeroStories_tileHeroStoriesButtonTitle__8Xiey']")
    private List<WebElement> slideTitles;

    @FindBy(css = ".slide-duration")
    private List<WebElement> slideDurations;

    public TicketsPage(WebDriver driver) {
        super(driver);
        logger.info("TicketsPage initialized.");
    }
    public void navigateToHomePage() {
        driver.get(derived_product1Url);
    }

    public int countSlides() {
        return slides.size();
    }

    public List<String> getSlideTitles() {
        List<String> titles = new ArrayList<>();
        for (WebElement titleElement : slideTitles) {

            titles.add(titleElement.getText());
        }
        return titles;
    }

    public List<Integer> getSlideDurations() {
        List<Integer> durations = new ArrayList<>();
        for (WebElement slide : slides) {
            // Check if duration is stored as an attribute
            String durationAttribute = slide.getAttribute("data-duration");
            if (durationAttribute != null && !durationAttribute.isEmpty()) {
                durations.add(Integer.parseInt(durationAttribute));
            } else {
                // Fallback: Assume default duration if not specified
                durations.add(0); // Replace 0 with a default value if needed
            }
        }
        return durations;
    }


}