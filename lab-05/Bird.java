import java.util.HashMap;

public class Bird extends Creature {

    private static HashMap<String, String> speciesRegistry = new HashMap<>();

    private boolean canFly;
    private String dietType;

    public Bird(String name, String size, String species, boolean canFly, String dietType) {
        super(name, size, species);
        this.canFly = canFly;
        this.dietType = dietType;
        registerSpecies(species);
    }

    public Bird(String species) {
        super(species, "Small");
        this.canFly = true;
        this.dietType = "omnivore";
        registerSpecies(species);
    }

    private static void registerSpecies(String species) {
        if (!speciesRegistry.containsKey(species)) {
            speciesRegistry.put(species, "Bird");
        }
    }

    @Override
    public void takeTurn() {
        String action = canFly ? "flies through the air" : "walks along the ground";
        System.out.println(getName() + " (" + getSpecies() + ") " + action + " looking for food (" + dietType + ").");
    }
}
