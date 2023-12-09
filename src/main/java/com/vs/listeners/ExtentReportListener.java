package com.vs.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.vs.appium.AppiumService;
import com.vs.reporting.ExtentReportNG;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener extends AppiumService implements ITestListener {

    ExtentReports extent = ExtentReportNG.getReporterObject();
    ExtentTest test;
    AppiumDriver driver;

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
        try {
            driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        String desFile = getScreenshot(result.getMethod().getMethodName(),driver);
//        test.addScreenCaptureFromPath(desFile,result.getMethod().getMethodName());
        test.log(Status.FAIL,result.getMethod().getMethodName(), MediaEntityBuilder.createScreenCaptureFromPath(desFile).build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
//        Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
//        for (ITestResult temp : failedTests) {
//            ITestNGMethod method = temp.getMethod();
//            if (context.getFailedTests().getResults(method).size() > 1) {
//                failedTests.remove(temp);
//            } else {
//                if (!context.getPassedTests().getResults(method).isEmpty()) {
//                    failedTests.remove(temp);
//                }
//            }
//        }
        extent.flush();
    }
}
