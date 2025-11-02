public class WorldTest {
    public static void main(String[] args) {
        Mammal wolf = new Mammal("Lobo", "Large", "Wolf", "carnivore");
        Bird sparrow = new Bird("Chirpy", "Small", "Sparrow", true, "omnivore");
        Fish salmon = new Fish("Nemo", "Medium", "Salmon", false, "insects");

        wolf.takeTurn();
        sparrow.takeTurn();
        salmon.takeTurn();
    }
}
