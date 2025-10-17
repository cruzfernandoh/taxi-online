package org.taxionline.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfigUtils {

    private final Properties props = new Properties();

    private AppConfigUtils() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties not found!");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration", e);
        }
    }

    private static final class InstanceHolder {
        private static final AppConfigUtils instance = new AppConfigUtils();
    }

    public static AppConfigUtils getInstance() {
        return InstanceHolder.instance;
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}
