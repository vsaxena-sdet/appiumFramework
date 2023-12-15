package com.vs.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FormPage extends AbstractPageClass {

    AppiumDriver driver;

//    public FormPage(AndroidDriver driver)
//    {
//        super(driver);
//        this.driver = driver;
//        PageFactory.initElements(new AppiumFieldDecorator(driver), this); //
//
//    }

    @AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(xpath="//android.widget.RadioButton[@text='Female']")
    private WebElement femaleOption;

    @AndroidFindBy(xpath="//android.widget.RadioButton[@text='Male']")
    private WebElement maleOption;

    @AndroidFindBy(id = "android:id/text1")
    private WebElement countrySelection;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement shopButton;

    public FormPage(AppiumDriver driver) {
        super(driver);
        this.driver = driver;

    }


    public void setNameField(String name)
    {
        nameField.sendKeys(name);
        ((AndroidDriver)driver).hideKeyboard();

    }

    public void setActivity()
    {
        Activity activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.MainActivity");
        ((AndroidDriver)driver).startActivity(activity);
    }

    public void setGender(String gender)
    {
        if (gender.contains("female"))
            femaleOption.click();
        else
            maleOption.click();

    }

    public void setCountrySelection(String countryName)

    {
        countrySelection.click();
        scrollToText(countryName);
        driver.findElement(By.xpath("//android.widget.TextView[@text='"+countryName+"']")).click();

    }

    public void submitForm()
    {
        shopButton.click();
    }







}

