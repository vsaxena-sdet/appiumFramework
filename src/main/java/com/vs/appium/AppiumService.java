package com.vs.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AppiumService {

    private final AppiumDriverLocalService appiumService;
    private final UiAutomator2Options options;

    private static final ThreadLocal<AppiumService> appiumServiceThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    public AppiumService() {
        options = new UiAutomator2Options();
        options.setDeviceName("emulator-5554");
        options.setApp("/Users/vaibhavsaxena/IdeaProjects/AutomationFM/src/test/resources/General-Store.apk");
        options.skipLogcatCapture();
        options.setSkipLogCapture(true);
        options.setSkipLogcatCapture(true);
        options.skipDeviceInitialization();
        options.setNoReset(false);
        options.setCapability("forceAppLaunch",true);

        appiumService = new AppiumServiceBuilder()
                .usingPort(0)
                .withCapabilities(options)
                .build();

        System.out.println("Appium server started at port:" + appiumService.getUrl().getPort());
    }

    public void startAppiumServer() {
        appiumService.start();
    }

    public AndroidDriver createDriver() {
        AndroidDriver driver = new AndroidDriver(appiumService, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    public void stopAppiumServer() {
        if (appiumService != null && appiumService.isRunning()) {
            appiumService.stop();
            System.out.println("Appium server stopped.");
        }
    }

    public static AppiumService getInstance() {
        if (appiumServiceThreadLocal.get() == null) {
            appiumServiceThreadLocal.set(new AppiumService());
        }
        return appiumServiceThreadLocal.get();
    }

    public static AppiumDriver getDriverInstance() {
        if (driverThreadLocal.get() == null) {
            AppiumService appiumService = getInstance();
            appiumService.startAppiumServer();
            driverThreadLocal.set(appiumService.createDriver());
        }
        return driverThreadLocal.get();
    }

    public void stopDriver() {
        if (driverThreadLocal != null) {
            driverThreadLocal.get().quit();
            System.out.println("Appium server stopped.");
        }
    }

    public String getScreenshot(String testCaseName, AppiumDriver driver) {
        File source = driver.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "/reports" + testCaseName + ".png";
        try {
            FileUtils.copyFile(source, new File(destinationFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destinationFile;
    }

}
