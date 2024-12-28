package com.veeva.framework.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVUtils {

    public static void writeToCSV(String filePath, List<String> data) throws IOException {
        // Ensure the directory structure exists
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean isCreated = parentDir.mkdirs();
            if (isCreated) {
                System.out.println("Created directory structure for file: " + filePath);
            } else {
                System.err.println("Failed to create directory structure for file: " + filePath);
            }
        }

        // Write data to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static Map<String, Long> findDuplicates(List<String> data) {
        return data.stream()
                .collect(Collectors.groupingBy(link -> link, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
