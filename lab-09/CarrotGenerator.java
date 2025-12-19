import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Lab 09 - Step 9
 * Uses a Swing Timer to periodically generate random "carrots" (orange squares).
 */
public class CarrotGenerator extends JPanel {
    private final List<Point> carrots = new ArrayList<>();
    private final Random rand = new Random();
    private final Timer timer;

    public CarrotGenerator() {
        setPreferredSize(new Dimension(700, 500));
        setBackground(Color.BLACK);

        // timer executes every 250 ms
        timer = new Timer(250, (ActionEvent e) -> {
            int x = rand.nextInt(Math.max(1, getWidth() - 10));
            int y = rand.nextInt(Math.max(1, getHeight() - 10));
            carrots.add(new Point(x, y));
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        for (Point p : carrots) {
            g.fillRect(p.x, p.y, 10, 10);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("CarrotGenerator");
            f.add(new CarrotGenerator());
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
