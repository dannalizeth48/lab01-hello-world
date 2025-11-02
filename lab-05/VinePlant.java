import java.util.ArrayList;
import java.util.Arrays;

public class VinePlant extends Plant {

    public VinePlant(String nickname) {
        super(
            nickname,
            "Medium",
            "Vine",
            true,
            "soft",
            new ArrayList<String>(Arrays.asList("cloning", "seeds")),
            "climber"
        );

        this.reproBehavior = new SeedReproduction(); // vines can seed and also clone
    }

    @Override
    public void takeTurn() {
        System.out.println(getName() + " (Vine) climbs and spreads along surfaces.");
        if (reproBehavior != null) {
            System.out.println("  -> " + getName() + " " + reproBehavior.reproduceAction() + ".");
        }
    }
}

