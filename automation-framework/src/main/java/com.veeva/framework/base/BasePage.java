package com.veeva.framework.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    /**
     * Executes JavaScript commands.
     *
     * @param script JavaScript command to execute.
     * @param args   Arguments to pass to the script.
     * @return Result of the script execution, if any.
     */
    protected Object executeJavaScript(String script, Object... args) {
        if (driver instanceof JavascriptExecutor) {
            return ((JavascriptExecutor) driver).executeScript(script, args);
        } else {
            throw new UnsupportedOperationException("Driver does not support JavaScript execution");
        }
    }
}

