package app;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest extends AppBase {


    @Test(groups = {"Smoke"})
    public void FillForm() {
        formPage.setNameField("Vaibhav");
        formPage.setGender("male");
        formPage.setCountrySelection("Argentina");
        formPage.submitForm();
        String toast = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
        Assert.assertEquals(toast,"Please enter your name");

    }
}
