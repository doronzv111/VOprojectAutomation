package org.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReaderProperties {
    private static Properties configFile;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            configFile = new Properties();
            configFile.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Failed to load properties file!");
        }
    }

    public static String get(String key) {
        return configFile.getProperty(key);
    }
}