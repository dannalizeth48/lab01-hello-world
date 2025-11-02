public abstract class Creature implements TurnTaker {

    protected String name;     // nickname: "Bear", "OldPine"
    protected String size;     // "Small", "Medium", "Large", etc.
    protected String species;  // species identifier like "Bear", "Pine", "Koi"

    // Constructor #1 (full)
    public Creature(String name, String size, String species) {
        this.name = name;
        this.size = size;
        this.species = species;
    }

    // Constructor #2 (shortcut): use species as the display name too
    public Creature(String species, String size) {
        this.name = species;
        this.size = size;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String newSize) {
        this.size = newSize;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String newSpecies) {
        this.species = newSpecies;
    }

    // Polymorphic action every turn.
    // IMPORTANT: abstract means subclasses MUST implement this.
    @Override
    public abstract void takeTurn();

    // Shared helper some subclasses may call
    public String eat() {
        return name + " (" + species + ") is eating.";
    }

    @Override
    public String toString() {
        return "[Creature type=" + this.getClass().getSimpleName()
             + ", name=" + name
             + ", species=" + species
             + ", size=" + size
             + "]";
    }
}
