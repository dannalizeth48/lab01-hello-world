import java.io.*;
import java.util.*;

public class ProcessCreatureFile {

    // Load creatures from a CSV file
    public static ArrayList<Creature> loadCreatures(String filename) {
        ArrayList<Creature> creatures = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String size = parts[1].trim();
                    creatures.add(new Creature(name, size));
                } else {
                    System.out.println("⚠ Skipping bad line " + lineNum + ": " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("❌ File not found: " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }

        return creatures;
    }

    // Save creatures to a CSV file
    public static void saveCreatures(String filename, ArrayList<Creature> creatures) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Creature c : creatures) {
                pw.println(c.getName() + "," + c.getSize());
            }
            System.out.println("✅ Saved " + creatures.size() + " creatures to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error writing file: " + e.getMessage());
        }
    }

    // Count by size
    public static void countBySize(ArrayList<Creature> creatures) {
        Map<String, Integer> counts = new HashMap<>();

        for (Creature c : creatures) {
            counts.put(c.getSize(), counts.getOrDefault(c.getSize(), 0) + 1);
        }

        System.out.println("\n--- Creature Counts by Size ---");
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ProcessCreatureFile <inputfile>");
            return;
        }

        String inputFile = args[0];
        ArrayList<Creature> creatures = loadCreatures(inputFile);

        if (creatures.isEmpty()) {
            System.out.println("No creatures loaded.");
            return;
        }

        // Print all creatures
        System.out.println("Loaded " + creatures.size() + " creatures:");
        for (Creature c : creatures) {
            System.out.println(c);
        }

        // Count by size
        countBySize(creatures);

        // Save to new file
        saveCreatures("creature-output.csv", creatures);
    }
}
