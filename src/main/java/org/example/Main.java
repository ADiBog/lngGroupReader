package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Please provide input file path as argument");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = "C:\\Users\\astonuser.MSI\\Downloads\\result.txt"; // указываем путь к файлу для записи

        long startTime = System.nanoTime();

        Map<String, Integer> groups = getStringIntegerMap(inputFilePath);

        int groupCount = 0;
        for (int count : groups.values()) {
            if (count > 1) {
                groupCount++;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            writer.println("Number of groups with more than one element: " + groupCount);
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Execution time: " + duration + " ms");
    }

    private static Map<String, Integer> getStringIntegerMap(String filePath) throws IOException {
        Map<String, Integer> groups = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        groups.put(part, groups.getOrDefault(part, 0) + 1);
                    }
                }
            }
        }
        return groups;
    }
}
