import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        Map<String, Integer> groups = getStringIntegerMap();

        int groupCount = 0;
        for (int count : groups.values()) {
            if (count > 1) {
                groupCount++;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\astonuser.MSI\\Downloads\\result.txt"))) {
            writer.println("Number of groups with more than one element: " + groupCount);
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Execution time: " + duration + " ms");
    }

    private static Map<String, Integer> getStringIntegerMap() throws IOException {
        Map<String, Integer> groups = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\astonuser.MSI\\Downloads\\lng.txt"))) {
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
