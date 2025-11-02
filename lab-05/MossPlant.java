import java.util.ArrayList;
import java.util.Arrays;

public class MossPlant extends Plant {

    public MossPlant(String nickname) {
        super(
            nickname,
            "Tiny",
            "Moss",
            true,                          // perennial-like ground cover
            "soft",
            new ArrayList<String>(Arrays.asList("spores")),
            "groundcover"
        );

        this.reproBehavior = new SporeReproduction();
    }

    @Override
    public void takeTurn() {
        System.out.println(getName() + " (Moss) hugs the damp ground.");
        if (reproBehavior != null) {
            System.out.println("  -> " + getName() + " " + reproBehavior.reproduceAction() + ".");
        }
    }
}
