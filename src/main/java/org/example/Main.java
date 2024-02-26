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

        Map<String, List<String>> groups = getStringListMap(inputFilePath);

        List<Map.Entry<String, List<String>>> groupList = new ArrayList<>(groups.entrySet());
        groupList.sort((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()));

        int groupCount = 0;
        for (Map.Entry<String, List<String>> entry : groupList) {
            if (entry.getValue().size() > 1) {
                groupCount++;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            writer.println("Number of groups with more than one element: " + groupCount);
            int groupId = 1;
            for (Map.Entry<String, List<String>> entry : groupList) {
                if (entry.getValue().size() > 1) {
                    writer.println("Group " + groupId);
                    for (String line : entry.getValue()) {
                        writer.println(line);
                    }
                    writer.println();
                    groupId++;
                }
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Execution time: " + duration + " ms");
    }

    private static Map<String, List<String>> getStringListMap(String filePath) throws IOException {
        Map<String, List<String>> groups = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        groups.computeIfAbsent(part, k -> new ArrayList<>()).add(line);
                    }
                }
            }
        }
        return groups;
    }
}
