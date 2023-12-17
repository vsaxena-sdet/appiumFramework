package com.vs.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class PropertyReader {

    String fileName;

    private final Properties properties;
    private static PropertyReader propertyReader;

    public PropertyReader() {
        properties = new Properties();
    }

    public static PropertyReader getInstance() {
        System.out.println("new Properties instance");
        if (propertyReader ==null){
            propertyReader = new PropertyReader();
        }
        return propertyReader;
    }

    public void load(File file) {
        InputStream input;
        try {
            input = Files.newInputStream(file.toPath());
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValue(String key) {
        try {
            return properties.getProperty(key).trim();
        } catch (Exception e) {
            throw new RuntimeException(key + " not found");
        }
    }
}