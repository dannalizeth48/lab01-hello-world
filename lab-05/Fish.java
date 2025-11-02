import java.util.HashMap;

public class Fish extends Creature {

    private static HashMap<String, String> speciesRegistry = new HashMap<>();

    private boolean saltWater;
    private String dietType;

    public Fish(String name, String size, String species, boolean saltWater, String dietType) {
        super(name, size, species);
        this.saltWater = saltWater;
        this.dietType = dietType;
        registerSpecies(species);
    }

    // Overloaded constructor
    public Fish(String species) {
        super(species, "Small");
        this.saltWater = false;
        this.dietType = "plankton";
        registerSpecies(species);
    }

    private static void registerSpecies(String species) {
        if (!speciesRegistry.containsKey(species)) {
            speciesRegistry.put(species, "Fish");
        }
    }

    @Override
    public void takeTurn() {
        String environment = saltWater ? "the ocean" : "a river";
        System.out.println(getName() + " (" + getSpecies() + ") swims in " + environment + " feeding on " + dietType + ".");
    }
}

