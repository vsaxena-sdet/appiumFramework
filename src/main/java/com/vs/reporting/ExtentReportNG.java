package com.vs.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {

    static ExtentReports extentReport;

    public static ExtentReports getReporterObject() {
        String path = System.getProperty("user.dir") + "//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("App Automation");
        reporter.config().setDocumentTitle("Test Results");
        extentReport = new ExtentReports();
        extentReport.attachReporter(reporter);
        extentReport.setSystemInfo("Test Run Initiated By", System.getProperty("user.name"));
        return extentReport;
    }
}
