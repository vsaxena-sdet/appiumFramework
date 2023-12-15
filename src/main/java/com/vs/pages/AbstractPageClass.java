package com.vs.pages;

import com.vs.appium.AppiumActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

/**
 * Base page class that has to be extended by each class
 */

public abstract class AbstractPageClass extends AppiumActions {

    /**
     *
     * @param driver driver object
     */

    public AbstractPageClass(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     *
     * @param driver driver object
     * @param secondsToWait is a desired duration of the waiting for an element presence
     */
    public AbstractPageClass(AppiumDriver driver, long secondsToWait) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(secondsToWait)), this);
    }
}
