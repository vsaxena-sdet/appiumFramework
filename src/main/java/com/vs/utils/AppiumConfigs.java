package com.vs.utils;

import java.io.File;

public class AppiumConfigs {

    public static final String DEVICE_NAME;
    public static final String PATH_TO_APK;
    public static final String PATH_TO_ANDROID_SDK;
    public static final String PATH_TO_CONFIG = "appium.properties";
    private static final PropertyReader propertyReader = PropertyReader.getInstance();

    static {
        propertyReader.load(new File(PATH_TO_CONFIG));
        DEVICE_NAME = loadProperty("deviceName");
        PATH_TO_APK = loadProperty("apkName");
        PATH_TO_ANDROID_SDK = loadProperty("ANDROID_HOME");

    }

    private static String loadProperty(String key) {
        String $ = System.getenv(key);
        String $1 = System.getProperty(key);
        String temp = ($ != null && !$.isEmpty()) ? $ : ($1 != null && !$1.isEmpty()) ? $1 : null;
        if (temp == null) {
            temp = propertyReader.getValue(key);
        }
        return temp;
    }


}
