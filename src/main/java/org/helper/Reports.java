package org.helper;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reports {
    private static ExtentReports extent;

    public static void setupReports() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Reports/ExtentReports.html");
        extent.attachReporter(spark);
    }

    public static ExtentTest createTest(String testName, String description) {
        return extent.createTest(testName, description);
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}