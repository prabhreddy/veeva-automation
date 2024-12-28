package com.veeva.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportManager {

    private static final Map<String, ExtentReports> extentReportsMap = new HashMap<>();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final String REPORTS_DIRECTORY = "target/extent-reports/";


    public static ExtentReports getReporter(String moduleName) {
        ensureReportsDirectoryExists(moduleName);
        if (!extentReportsMap.containsKey(moduleName)) {
            System.out.println("Initializing Extent Report for module: " + moduleName);
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORTS_DIRECTORY + moduleName + "-Report.html");
            sparkReporter.config().setReportName(moduleName + " Automation Report");
            sparkReporter.config().setDocumentTitle(moduleName + " Test Execution Report");

            ExtentReports extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Environment", "QA");
            extentReports.setSystemInfo("Tester", "Prabhakar Reddy");

            extentReportsMap.put(moduleName, extentReports);
        }
        return extentReportsMap.get(moduleName);
    }

    public static ExtentTest createTest(String moduleName, String testName) {
        ExtentTest test = getReporter(moduleName).createTest(testName);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flush(String moduleName) {
        if (moduleName == null || moduleName.trim().isEmpty()) {
            System.err.println("Module name is null or empty. Cannot flush report.");
            return; // Skip flushing for invalid module names
        }

        if (extentReportsMap.containsKey(moduleName)) {
            extentReportsMap.get(moduleName).flush();
            System.out.println("Flushed Extent Report for module: " + moduleName);
        } else {
            System.err.println("No Extent Report found for module: " + moduleName);
        }
    }

    public static void flushAllReports() {
        for (Map.Entry<String, ExtentReports> entry : extentReportsMap.entrySet()) {
            String moduleName = entry.getKey();
            ExtentReports extentReports = entry.getValue();
            extentReports.flush();
            System.out.println("Flushed Extent Report for module: " + moduleName);
        }
    }

    private static void ensureReportsDirectoryExists(String moduleName) {
        File directory = new File(REPORTS_DIRECTORY);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (isCreated) {
                System.out.println("Created reports directory: " + REPORTS_DIRECTORY);
            } else {
                System.err.println("Failed to create reports directory!");
            }
        }
    }
}
