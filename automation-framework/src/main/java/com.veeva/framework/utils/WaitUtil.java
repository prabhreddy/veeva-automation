package com.veeva.framework.utils;

import com.veeva.framework.config.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class WaitUtil {

    private static long implicitWait = Long.parseLong(ConfigLoader.getInstance().getConfigValue("waits.implicit_wait"));
    private static long explicitWait = Long.parseLong(ConfigLoader.getInstance().getConfigValue("waits.explicit_wait"));
    private static long fluentWaitTimeout = Long.parseLong(ConfigLoader.getInstance().getConfigValue("waits.fluent_wait_timeout"));
    private static long fluentWaitPolling = Long.parseLong(ConfigLoader.getInstance().getConfigValue("waits.fluent_wait_polling"));

    /**
     * Sets the implicit wait for WebDriver.
     *
     * @param driver WebDriver instance.
     */
    public static void setImplicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
    }

    /**
     * Waits for an element to be visible using explicit wait.
     *
     * @param driver WebDriver instance.
     * @param element WebElement to wait for.
     */
    public static void waitForElementToBeVisible(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(explicitWait))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Creates a FluentWait instance.
     *
     * @param driver WebDriver instance.
     * @return FluentWait instance.
     */
    public static FluentWait<WebDriver> getFluentWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(fluentWaitTimeout))
                .pollingEvery(Duration.ofSeconds(fluentWaitPolling))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Waits for an element to be visible using FluentWait.
     *
     * @param driver WebDriver instance.
     * @param element WebElement to wait for.
     */
    public static void waitForElementWithFluentWait(WebDriver driver, WebElement element) {
        getFluentWait(driver).until(ExpectedConditions.visibilityOf(element));
    }
}
