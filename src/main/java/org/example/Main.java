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
        String outputFilePath = "result.txt"; // указываем путь к файлу для записи

        long startTime = System.nanoTime();

        Map<String, Set<String>> groups = getStringSetMap(inputFilePath);

        List<Map.Entry<String, Set<String>>> groupList = new ArrayList<>(groups.entrySet());
        groupList.sort((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()));

        int groupCount = 0;
        for (Map.Entry<String, Set<String>> entry : groupList) {
            if (entry.getValue().size() > 1) {
                groupCount++;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            writer.println("Number of groups with more than one element: " + groupCount);
            int groupId = 1;
            for (Map.Entry<String, Set<String>> entry : groupList) {
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

    // Метод для чтения файла и группировки строк по ключам
    private static Map<String, Set<String>> getStringSetMap(String filePath) throws IOException {
        // Создаем карту для хранения групп
        Map<String, Set<String>> groups = new LinkedHashMap<>();

        // Читаем файл построчно
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Разбиваем строку на части по разделителю ";"
                String[] parts = line.split(";");
                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    // Проверяем, что часть строки не пустая
                    if (!part.isEmpty()) {
                        // Добавляем строку в соответствующую группу
                        groups.computeIfAbsent(part + "-" + i, k -> new HashSet<>()).add(line);
                    }
                }
            }
        }
        return groups;
    }
}
