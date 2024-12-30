package com.veeva.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentTest;

import java.io.File;

public class ReportManager {

    private static ExtentReports extentReports;
    private static ExtentTest test;

    /**
     * Initializes and returns the ExtentReports instance.
     *
     * @return ExtentReports instance
     */
    public static ExtentReports getInstance() {
        if (extentReports == null) {
            // Specify the report file location
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/reports/extent-report.html");
            sparkReporter.config().setReportName("Automation Test Report");
            sparkReporter.config().setDocumentTitle("Automation Framework");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
        return extentReports;
    }

    /**
     * Sets the current test instance for logging.
     *
     * @param testInstance ExtentTest instance
     */
    public static void setTest(ExtentTest testInstance) {
        test = testInstance;
    }

    /**
     * Logs a message to the report.
     *
     * @param message The message to log
     */
    public static void logInfo(String message) {
        if (test != null) {
            test.info(message);
        }
    }

    /**
     * Attach a screenshot to the report.
     *
     * @param screenshotPath Path to the screenshot file
     */
    public static void attachScreenshot(String screenshotPath) {
        if (test != null && new File(screenshotPath).exists()) {
            try {
                test.info("Attached Screenshot",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                logInfo("Failed to attach screenshot: " + e.getMessage());
            }
        } else {
            logInfo("Screenshot file not found: " + screenshotPath);
        }
    }

    /**
     * Attach a text file to the report.
     *
     * @param filePath Path to the text file
     */
    public static void attachTextFile(String filePath) {
        if (test != null && new File(filePath).exists()) {
            test.info("Attached File: " + filePath);
        } else {
            logInfo("Text file not found: " + filePath);
        }
    }
}
