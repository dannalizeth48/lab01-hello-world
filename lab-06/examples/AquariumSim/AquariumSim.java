import javax.swing.*;
import java.awt.*;

/**
 * Top-level window for the Aquarium simulation.
 * This class is responsible only for creating the frame
 * and embedding the AquariumPanel (the world / simulation).
 */
public class AquariumSim extends JFrame {

    /**
     * Launches the Aquarium simulation.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AquariumSim sim = new AquariumSim();
            sim.setVisible(true);
        });
    }

    /**
     * Constructs the main window and attaches the AquariumPanel.
     */
    public AquariumSim() {
        super("Aquarium Simulation - Decomposition, Appetite, Smooth Gliding");

        // Basic frame setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLocationByPlatform(true);

        // Create the simulation panel (world) and HUD
        AquariumPanel panel = new AquariumPanel(960, 620);

        // Layout: panel in center, HUD at the bottom
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(panel.buildHUD(), BorderLayout.SOUTH);
    }
}
