package com.vs.appium;

import com.vs.utils.AppiumConfigs;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AppiumService extends AppiumConfigs {

    private final AppiumDriverLocalService appiumService;
    private final UiAutomator2Options options;
    private static final ThreadLocal<AppiumService> appiumServiceThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    public AppiumService() {
        options = new UiAutomator2Options();
        options.setDeviceName(DEVICE_NAME);
        options.setApp(PATH_TO_APK);
        options.skipLogcatCapture();
        options.setSkipLogCapture(true);
        options.setSkipLogcatCapture(true);
        options.skipDeviceInitialization();
        options.setNoReset(true);
        options.setCapability("forceAppLaunch", true);

        appiumService = new AppiumServiceBuilder()
                .usingPort(0)
                .withCapabilities(options)
                .build();

//        appiumService = AppiumDriverLocalService
//                .buildService(new AppiumServiceBuilder()
//                        .usingDriverExecutable(new File("/usr/local/bin/node"))
//                        .withCapabilities(options)
//                        .withAppiumJS(
//                                new File(
//                                        "/usr/local/lib/node_modules/appium/build/lib/main.js"))
//                        .withIPAddress("127.0.0.1").usingAnyFreePort());
//        appiumService.start();

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

    public static void startEmulator() {
        String startEmulatorCommand = PATH_TO_ANDROID_SDK + "/emulator/emulator -avd " + DEVICE_NAME;
        System.out.println("startEmulatorCommand " + startEmulatorCommand);
        if (!isEmulatorRunning()) {
            try {
                Runtime.getRuntime().exec(startEmulatorCommand);
                TimeUnit.SECONDS.sleep(30);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void stopEmulator() {
        String stopEmulatorCommand = PATH_TO_ANDROID_SDK + "/platform-tools/adb -s " + DEVICE_NAME + " emu kill";
        System.out.println("stopEmulatorCommand" + stopEmulatorCommand);
        try {
            Runtime.getRuntime().exec(stopEmulatorCommand);
            TimeUnit.SECONDS.sleep(10);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isEmulatorRunning() {
        String adbCommand = PATH_TO_ANDROID_SDK + "/platform-tools/adb devices";
        Process process;
        try {
            process = Runtime.getRuntime().exec(adbCommand);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line.contains(DEVICE_NAME)) {
                return true; // Emulator is already running
            }
        }
        return false; // Emulator is not running
    }
}
