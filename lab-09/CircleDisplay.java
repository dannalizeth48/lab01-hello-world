import javax.swing.*;
import java.awt.*;

/**
 * Lab 09 - Step 5
 * Basic Swing drawing example: draws a circle with adjustable radius.
 */
public class CircleDisplay extends JPanel {
    private int radius = 80;

    public CircleDisplay() {
        setPreferredSize(new Dimension(500, 400));
        setBackground(Color.BLACK);
    }

    public void setRadius(int r) {
        radius = Math.max(5, Math.min(180, r));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        int x = (getWidth() - 2 * radius) / 2;
        int y = (getHeight() - 2 * radius) / 2;
        g.fillOval(x, y, 2 * radius, 2 * radius);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("CircleDisplay");
            CircleDisplay panel = new CircleDisplay();
            f.add(panel, BorderLayout.CENTER);

            JSlider slider = new JSlider(10, 180, 80);
            slider.addChangeListener(e -> panel.setRadius(slider.getValue()));
            f.add(slider, BorderLayout.SOUTH);

            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
