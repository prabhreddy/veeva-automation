package com.veeva.framework.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ConfigLoader {

    private static ConfigLoader instance;
    private Map<String, Object> config;

    private ConfigLoader() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml")) {
            config = yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.yaml: " + e.getMessage(), e);
        }
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public String getConfigValue(String key) {
        Object value = getConfig(key);
        return value != null ? value.toString() : null;
    }

    public boolean getBooleanConfigValue(String key) {
        Object value = getConfig(key);
        return value != null && Boolean.parseBoolean(value.toString());
    }

    public int getIntConfigValue(String key) {
        Object value = getConfig(key);
        if (value != null) {
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Configuration value for key '" + key + "' is not a valid integer: " + value, e);
            }
        }
        throw new RuntimeException("Configuration value for key '" + key + "' is missing or null");
    }

    private Object getConfig(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = config;
        Object value = null;
        for (String k : keys) {
            value = currentMap.get(k);
            if (value instanceof Map) {
                currentMap = (Map<String, Object>) value;
            } else {
                break;
            }
        }
        return value;
    }

    public List<Integer> getIntListConfigValue(String key) {
        Object value = getConfigValue(key);
        if (value instanceof List) {
            return (List<Integer>) value;
        }
        throw new IllegalArgumentException("Key does not map to a List<Integer> value: " + key);
    }


}
