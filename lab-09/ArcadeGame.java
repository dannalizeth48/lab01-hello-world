import javax.swing.*;

/**
 * Lab 09 - Step 15
 * Launches the refactored arcade ecosystem simulation.
 */
public class ArcadeGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Arcade Ecosystem (Refactored)");
            Ecosystem eco = new Ecosystem();
            frame.add(eco);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            eco.requestFocusInWindow();
        });
    }
}
