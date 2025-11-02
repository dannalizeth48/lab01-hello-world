import java.io.*;
import java.util.ArrayList;

public class CreatureRegistry {
    private ArrayList<Creature> data;
    private String filename;

    // Constructor: loads the file
    public CreatureRegistry(String filename) {
        this.filename = filename;
        this.data = new ArrayList<>();
        load();
    }

    // Load data from CSV
    private void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String size = parts[1].trim();
                    data.add(new Creature(name, size));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
    }

    // Save data to CSV
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Creature c : data) {
                bw.write(c.getName() + "," + c.getSize());
                bw.newLine();
            }
            System.out.println("✔ Saved " + data.size() + " creatures to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + filename);
        }
    }

    // Return number of creatures
    public int size() {
        return data.size();
    }

    // Return a COPY of the creature so caller can't mutate internal list directly
    public Creature get(int index) {
        if (index < 0 || index >= data.size()) return null;
        Creature original = data.get(index);
        return new Creature(original.getName(), original.getSize());
    }

    // Add a new creature
    public void add(Creature c) {
        data.add(c);
    }

    // Update an existing creature
    public void update(int index, Creature c) {
        if (index >= 0 && index < data.size()) {
            data.set(index, c);
        } else {
            System.out.println("⚠ Invalid index for update.");
        }
    }

    // Delete a creature
    public void delete(int index) {
        if (index >= 0 && index < data.size()) {
            data.remove(index);
        } else {
            System.out.println("⚠ Invalid index for delete.");
        }
    }

    // Simple test main
    public static void main(String[] args) {
        CreatureRegistry reg = new CreatureRegistry("creature-data.csv");
        System.out.println("Loaded " + reg.size() + " creatures.");

        // Add a new creature
        reg.add(new Creature("Hydra", "Large"));
        System.out.println("After add: " + reg.size());

        // Update a creature at index 0
        reg.update(0, new Creature("Dragon", "Huge"));
        System.out.println("After update: " + reg.get(0));

        // Delete creature at index 1
        reg.delete(1);
        System.out.println("After delete: " + reg.size());

        // Save back to file
        reg.save();
    }
}
