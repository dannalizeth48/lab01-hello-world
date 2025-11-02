public class MiniSim {
    public static void main(String[] args) {

        // World of size 2x2
        World world = new World(2, 2);

        // Put some creatures in tile (0,0)
        Tile t00 = world.getTile(0, 0);
        t00.addCreature(new Mammal("Bear", "Large", "Bear", "omnivore"));
        t00.addCreature(new Bird("Hawk", "Medium", "Hawk", true, "carnivore"));

        // Put some creatures in tile (1,1)
        Tile t11 = world.getTile(1, 1);
        t11.addCreature(new Fish("Koi", "Small", "Koi", false, "algae"));
        t11.addCreature(new PinePlant("OldPine"));

        // Run 3 turns
        for (int i = 1; i <= 3; i++) {
            System.out.println("\nTURN " + i);
            world.takeTurn();
        }
    }
}
