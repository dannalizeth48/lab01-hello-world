import java.util.ArrayList;

public class Tile implements TurnTaker {

    // environment
    private int water;        // 0-100
    private int temperature;  // maybe degrees
    private int nutrient;     // 0-100 fertility

    // who lives here
    private ArrayList<Creature> creatures;

    public Tile(int water, int temperature, int nutrient) {
        this.water = water;
        this.temperature = temperature;
        this.nutrient = nutrient;
        this.creatures = new ArrayList<>();
    }

    public void addCreature(Creature c) {
        creatures.add(c);
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public int getWater() {
        return water;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getNutrient() {
        return nutrient;
    }

    // This is the key. Every turn, the tile advances one step.
    // It also asks each creature on the tile to take its turn.
    @Override
    public void takeTurn() {
        // Simple placeholder logic for now:
        System.out.println("[Tile] env(water=" + water +
                           ", temp=" + temperature +
                           ", nutrient=" + nutrient + ") tick");

        // Let each creature act. In the future this could depend on env.
        for (Creature c : creatures) {
            c.takeTurn();
        }
    }

    @Override
    public String toString() {
        return "Tile{water=" + water +
               ", temp=" + temperature +
               ", nutrient=" + nutrient +
               ", creatures=" + creatures +
               "}";
    }
}
