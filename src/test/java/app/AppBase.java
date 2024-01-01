package app;

import com.vs.appium.AppiumService;
import com.vs.pages.FormPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;

public class AppBase extends AppiumService {

    public FormPage formPage;
    public AndroidDriver driver;

    @BeforeSuite
    public void beforeClassSetup() {
        startEmulator();
        getInstance();
        driver = (AndroidDriver) getDriverInstance();
        formPage = new FormPage(driver);
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.terminateApp("com.androidsample.generalstore");
        driver.activateApp("com.androidsample.generalstore");
    }


    @AfterSuite
    public void afterClassSetup() {
        stopAppiumServer();
        stopDriver();
        stopEmulator();
    }
}
