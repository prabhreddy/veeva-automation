package com.veeva.derivedproduct2.pages;

import com.veeva.framework.base.BasePage;
import com.veeva.framework.config.ConfigLoader;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FooterPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(FooterPage.class);
    String derived_product2Url = ConfigLoader.getInstance().getConfigValue("urls.derived_product2");
    @FindBy(tagName = "a")
    private List<WebElement> footerLinks;

    public FooterPage(WebDriver driver) {
        super(driver);
        logger.info("HomePage initialized.");
    }

    public void navigateToHomePage() {
        driver.get(derived_product2Url);
    }

    public void scrollToFooter() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public List<String> getFooterLinks() {
        List<WebElement> freshFooterLinks = footerLinks.stream()
                .filter(element -> element.isDisplayed() && element.isEnabled())
                .collect(Collectors.toList());

        List<String> links = new ArrayList<>();
        for (WebElement element : freshFooterLinks) {
            try {
                String href = element.getAttribute("href");
                if (href != null && !href.isEmpty()) {
                    links.add(href);
                }
            } catch (StaleElementReferenceException e) {
                logger.warn("Encountered stale element. Skipping.");
            }
        }

        logger.info("Fetched {} footer links.", links.size());
        return links;
    }


}
