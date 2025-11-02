import java.util.ArrayList;
import java.util.Arrays;

public class PinePlant extends Plant {

    public PinePlant(String nickname) {
        super(
            nickname,                 // name shown
            "Large",                  // size
            "Pine",                   // species
            true,                     // perennial
            "woody",                  // stemType
            new ArrayList<String>(Arrays.asList("seeds")),
            "tall"                    // growthStyle
        );

        // attach composition behavior
        this.reproBehavior = new SeedReproduction();
    }

    @Override
    public void takeTurn() {
        System.out.println(getName() + " (Pine) makes needles and stores energy.");
        if (reproBehavior != null) {
            System.out.println("  -> " + getName() + " " + reproBehavior.reproduceAction() + ".");
        }
    }
}

