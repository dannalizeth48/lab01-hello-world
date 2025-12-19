import javax.swing.*;
import java.awt.*;

/**
 * Lab 09 - Step 7
 * Draws a Sierpinski triangle recursively.
 */
public class SierpinskiTriangle extends JPanel {
    private int depth = 7;

    public SierpinskiTriangle() {
        setPreferredSize(new Dimension(700, 600));
        setBackground(Color.BLACK);
    }

    private void drawTriangle(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3, int d) {
        if (d == 0) {
            Polygon p = new Polygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
            g.fillPolygon(p);
            return;
        }
        int mx12 = (x1 + x2) / 2, my12 = (y1 + y2) / 2;
        int mx23 = (x2 + x3) / 2, my23 = (y2 + y3) / 2;
        int mx31 = (x3 + x1) / 2, my31 = (y3 + y1) / 2;

        drawTriangle(g, x1, y1, mx12, my12, mx31, my31, d - 1);
        drawTriangle(g, mx12, my12, x2, y2, mx23, my23, d - 1);
        drawTriangle(g, mx31, my31, mx23, my23, x3, y3, d - 1);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.WHITE);

        int x1 = getWidth() / 2, y1 = 40;
        int x2 = 60, y2 = getHeight() - 60;
        int x3 = getWidth() - 60, y3 = getHeight() - 60;

        drawTriangle(g, x1, y1, x2, y2, x3, y3, depth);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("SierpinskiTriangle");
            f.add(new SierpinskiTriangle());
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
