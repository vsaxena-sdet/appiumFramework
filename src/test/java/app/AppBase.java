package app;

import com.vs.appium.AppiumService;
import com.vs.pages.FormPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class AppBase extends AppiumService {

    public FormPage formPage;
    public AndroidDriver driver;


    @BeforeClass
    public void beforeClassSetup() {
        getInstance();
        driver = (AndroidDriver) getDriverInstance();
        formPage = new FormPage(driver);
    }


    @AfterClass
    public void afterClassSetup() {
        stopAppiumServer();
        stopDriver();
    }
}
