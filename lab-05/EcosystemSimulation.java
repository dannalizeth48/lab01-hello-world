import java.io.*;
import java.util.*;

public class EcosystemSimulation {

    public static void main(String[] args) {
        // 1. Leer el archivo JSON
        String json = readFile("world-config.json");

        // 2. Obtener filas y columnas
        int rows = parseInt(json, "\"rows\"");
        int cols = parseInt(json, "\"cols\"");

        World world = new World(rows, cols);

        // 3. Buscar cada criatura dentro del JSON
        int index = 0;
        while ((index = json.indexOf("\"kind\"", index)) != -1) {
            int start = json.indexOf(":", index) + 1;
            int end = json.indexOf(",", start);
            String kind = getValue(json, start, end).replace("\"", "").trim();

            String name = getValueByKey(json, "\"name\"");
            String size = getValueByKey(json, "\"size\"");
            String species = getValueByKey(json, "\"species\"");
            int row = parseInt(json, "\"tileRow\"");
            int col = parseInt(json, "\"tileCol\"");

            Creature creature = createCreature(kind, name, size, species);
            world.getTile(row, col).addCreature(creature);

            index = end;
        }

        // 4. Simular 100 turnos
        for (int t = 1; t <= 100; t++) {
            System.out.println("\n=== TURN " + t + " ===");
            world.takeTurn();
        }

        System.out.println("Simulation complete. Report written to final-report.txt");
    }

    private static Creature createCreature(String kind, String name, String size, String species) {
        switch (kind.toLowerCase()) {
            case "mammal": return new Mammal(name, size, species, "omnivore");
            case "bird": return new Bird(name, size, species, true, "carnivore");
            case "fish": return new Fish(name, size, species, false, "algae");
            case "pineplant": return new PinePlant(name);
            case "vineplant": return new VinePlant(name);
            case "mossplant": return new MossPlant(name);
            default: return new PinePlant(name);
        }
    }

    // ======== Helpers para leer JSON plano ========
    private static String readFile(String filename) {
        try {
            return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filename)));
        } catch (IOException e) {
            System.out.println("Could not read JSON file: " + filename);
            return "";
        }
    }

    private static int parseInt(String json, String key) {
        try {
            int idx = json.indexOf(key);
            if (idx == -1) return 0;
            int start = json.indexOf(":", idx) + 1;
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return Integer.parseInt(json.substring(start, end).replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private static String getValue(String json, int start, int end) {
        if (end == -1) end = json.indexOf("}", start);
        return json.substring(start, end);
    }

    private static String getValueByKey(String json, String key) {
        int idx = json.indexOf(key);
        if (idx == -1) return "";
        int start = json.indexOf(":", idx) + 1;
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return json.substring(start, end).replace("\"", "").trim();
    }
}
