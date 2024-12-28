package com.veeva.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestDataLoader {
    private static final Properties properties = new Properties();
    private static final String TEST_DATA_FILE = "testData.properties";

    static {
        try (InputStream input = TestDataLoader.class.getClassLoader().getResourceAsStream(TEST_DATA_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Test data file not found: " + TEST_DATA_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data file: " + TEST_DATA_FILE, e);
        }
    }

    public static List<String> getStringList(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Key not found or empty in test data file: " + key);
        }
        return new ArrayList<>(Arrays.asList(value.split(",\\s*"))); // Split by comma and trim spaces
    }
}
