import java.util.ArrayList;
import java.util.HashMap;

/**
 * Plant
 * - extends Creature
 * - has traits like perennial, stemType, reproduction modes, growth style
 * - uses a static species registry to define species templates
 * - overrides takeTurn()
 * - shows constructor overloading that calls super(...)
 *
 * We also attach a reproduction behavior object (composition) in Paso 3.
 */
public class Plant extends Creature {

    // ===== SPECIES REGISTRY =====
    // speciesName -> SpeciesInfo (template describing that species)
    private static HashMap<String, SpeciesInfo> registry = new HashMap<>();

    // Static method to define a species template exactly once
    public static void registerSpecies(SpeciesInfo info) throws Exception {
        if (registry.containsKey(info.speciesName)) {
            throw new Exception("Species already exists: " + info.speciesName);
        }
        registry.put(info.speciesName, info);
    }

    // Look up a template by species name
    public static SpeciesInfo getSpeciesInfo(String speciesName) {
        return registry.get(speciesName);
    }

    // ===== INSTANCE FIELDS =====
    protected boolean perennial;
    protected String stemType; // "woody", "soft"
    protected ArrayList<String> reproductionModes; // ["seeds","spores","cloning",...]
    protected String growthStyle; // "tall","climber","groundcover","tiny"
    protected ReproductionBehavior reproBehavior; // composition (set in subclasses, step 3)

    // ===== CONSTRUCTOR #1 (full custom definition) =====
    public Plant(String name,
                 String size,
                 String species,
                 boolean perennial,
                 String stemType,
                 ArrayList<String> reproductionModes,
                 String growthStyle) {

        super(name, size, species); // call Creature constructor
        this.perennial = perennial;
        this.stemType = stemType;
        this.reproductionModes = reproductionModes;
        this.growthStyle = growthStyle;
        this.reproBehavior = null; // can be set later
    }

    // ===== CONSTRUCTOR #2 (build from registered species template) =====
    // If the species template exists in registry, copy its traits.
    // Otherwise fall back to some safe defaults.
    public Plant(String speciesTemplateName) {
        super(speciesTemplateName, "Unknown"); // name = species, size placeholder

        SpeciesInfo info = registry.get(speciesTemplateName);
        if (info == null) {
            this.perennial = false;
            this.stemType = "soft";
            this.reproductionModes = new ArrayList<>();
            this.reproductionModes.add("seeds");
            this.growthStyle = "unknown";
            this.size = "Unknown";
        } else {
            this.perennial = info.perennial;
            this.stemType = info.stemType;
            this.reproductionModes = new ArrayList<>(info.reproductionModes);
            this.growthStyle = info.growthStyle;
            this.size = info.sizeClass;
        }

        this.reproBehavior = null; // can be set later
    }

    // ===== OVERRIDE takeTurn =====
    @Override
    public void takeTurn() {
        // plant action per turn; could depend on traits in future
        System.out.println(getName() + " (" + getSpecies() + ") makes needles and stores energy.");
        // if we have a reproduction behavior, mention it (ties to Paso 3)
        if (reproBehavior != null) {
            System.out.println("  -> " + getName() + " " + reproBehavior.reproduceAction() + ".");
        }
    }

    // Helpful debug
    public String describe() {
        return "[Plant name=" + name +
               ", species=" + species +
               ", size=" + size +
               ", perennial=" + perennial +
               ", stemType=" + stemType +
               ", reproductionModes=" + reproductionModes +
               ", growthStyle=" + growthStyle +
               "]";
    }

    @Override
    public String toString() {
        return describe();
    }

    // ===== SpeciesInfo template =====
    public static class SpeciesInfo {
        public String speciesName;
        public boolean perennial;
        public String stemType;
        public ArrayList<String> reproductionModes;
        public String growthStyle;
        public String sizeClass; // "Tiny","Medium","Large"

        public SpeciesInfo(String speciesName,
                           boolean perennial,
                           String stemType,
                           ArrayList<String> reproductionModes,
                           String growthStyle,
                           String sizeClass) {
            this.speciesName = speciesName;
            this.perennial = perennial;
            this.stemType = stemType;
            this.reproductionModes = reproductionModes;
            this.growthStyle = growthStyle;
            this.sizeClass = sizeClass;
        }
    }

    // We'll define ReproductionBehavior interface and implementations in Paso 3.
    public interface ReproductionBehavior {
        String reproduceAction();
    }
}

