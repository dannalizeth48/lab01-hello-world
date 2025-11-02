import java.util.*;

public class CreatureCLI {

    // Print how to use this program
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java CreatureCLI list");
        System.out.println("  java CreatureCLI get <index>");
        System.out.println("  java CreatureCLI add <name> <size>");
        System.out.println("  java CreatureCLI update <index> <name> <size>");
        System.out.println("  java CreatureCLI delete <index>");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java CreatureCLI list");
        System.out.println("  java CreatureCLI get 2");
        System.out.println("  java CreatureCLI add Hydra Large");
        System.out.println("  java CreatureCLI update 0 Dragon Huge");
        System.out.println("  java CreatureCLI delete 3");
    }

    public static void main(String[] args) {

        // We always operate on the CSV file your lab is using
        String filename = "creature-data.csv";
        CreatureRegistry reg = new CreatureRegistry(filename);

        if (args.length == 0) {
            System.out.println("Error: missing command.");
            printUsage();
            System.exit(1);
        }

        String command = args[0].toLowerCase();

        try {

            // -----------------
            // list
            // -----------------
            if (command.equals("list")) {
                // Print all creatures with their index in the registry
                for (int i = 0; i < reg.size(); i++) {
                    Creature c = reg.get(i);
                    System.out.println(i + ": " + c);
                }
                // list does not change data so no save() call
                System.exit(0);
            }

            // -----------------
            // get <index>
            // -----------------
            if (command.equals("get")) {
                if (args.length != 2) {
                    System.out.println("Error: get needs an index.");
                    printUsage();
                    System.exit(1);
                }

                int index = Integer.parseInt(args[1]);
                Creature c = reg.get(index);
                if (c == null) {
                    System.out.println("Error: index out of range.");
                    System.exit(1);
                } else {
                    System.out.println(index + ": " + c);
                    System.exit(0);
                }
            }

            // -----------------
            // add <name> <size>
            // -----------------
            if (command.equals("add")) {
                if (args.length != 3) {
                    System.out.println("Error: add needs <name> <size>.");
                    printUsage();
                    System.exit(1);
                }

                String name = args[1];
                String size = args[2];

                Creature newCreature = new Creature(name, size);
                reg.add(newCreature);
                reg.save(); // persist change

                System.out.println("Added: " + newCreature);
                System.exit(0);
            }

            // -----------------
            // update <index> <name> <size>
            // -----------------
            if (command.equals("update")) {
                if (args.length != 4) {
                    System.out.println("Error: update needs <index> <name> <size>.");
                    printUsage();
                    System.exit(1);
                }

                int index = Integer.parseInt(args[1]);
                String name = args[2];
                String size = args[3];

                Creature updated = new Creature(name, size);
                // Check index range manually
                if (index < 0 || index >= reg.size()) {
                    System.out.println("Error: index out of range.");
                    System.exit(1);
                }

                reg.update(index, updated);
                reg.save(); // persist change

                System.out.println("Updated index " + index + " -> " + updated);
                System.exit(0);
            }

            // -----------------
            // delete <index>
            // -----------------
            if (command.equals("delete")) {
                if (args.length != 2) {
                    System.out.println("Error: delete needs <index>.");
                    printUsage();
                    System.exit(1);
                }

                int index = Integer.parseInt(args[1]);

                if (index < 0 || index >= reg.size()) {
                    System.out.println("Error: index out of range.");
                    System.exit(1);
                }

                reg.delete(index);
                reg.save(); // persist change

                System.out.println("Deleted index " + index + ".");
                System.exit(0);
            }

            // -----------------
            // unknown command
            // -----------------
            System.out.println("Error: unknown command \"" + command + "\"");
            printUsage();
            System.exit(1);

        } catch (NumberFormatException e) {
            System.out.println("Error: index must be an integer.");
            System.exit(1);
        }
    }
}
