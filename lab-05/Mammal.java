import java.util.HashMap;

public class Mammal extends Creature {

    private static HashMap<String, String> speciesRegistry = new HashMap<>();

    private boolean warmBlooded = true;
    private boolean laysEggs = false;
    private String dietType; // herbivore, carnivore, omnivore

    public Mammal(String name, String size, String species, String dietType) {
        super(name, size, species);
        this.dietType = dietType;
        registerSpecies(species);
    }

    // Overloaded constructor
    public Mammal(String species) {
        super(species, "Medium");
        this.dietType = "omnivore";
        registerSpecies(species);
    }

    private static void registerSpecies(String species) {
        if (!speciesRegistry.containsKey(species)) {
            speciesRegistry.put(species, "Mammal");
        }
    }

    @Override
    public void takeTurn() {
        System.out.println(getName() + " (" + getSpecies() + ") roams and eats (" + dietType + ").");
    }
}

