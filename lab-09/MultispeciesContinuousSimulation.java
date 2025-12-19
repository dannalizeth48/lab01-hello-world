import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Lab 09 - Step 13
 * Minimal 3-species continuous-style simulation (Euler stepping) to produce smooth curves.
 * This is intentionally simple for CSC210 lab submission.
 */
public class MultispeciesContinuousSimulation extends JPanel {
    private static final int STEPS = 600;
    private double t = 0;

    // populations
    private double A = 0.2; // carrots-ish
    private double B = 0.2; // rabbits-ish
    private double C = 0.2; // wolves-ish

    // parameters (tune-able)
    private double rA = 1.6, rB = 1.3, rC = 1.1;
    private double kA = 1.0, kB = 1.0, kC = 1.0;

    // interactions
    private double eatAB = 0.9; // B consumes A
    private double eatBC = 0.7; // C consumes B

    private final double[] aHist = new double[STEPS];
    private final double[] bHist = new double[STEPS];
    private final double[] cHist = new double[STEPS];
    private int idx = 0;

    public MultispeciesContinuousSimulation() {
        setPreferredSize(new Dimension(900, 600));
        setBackground(Color.BLACK);

        Timer timer = new Timer(16, (ActionEvent e) -> step());
        timer.start();
    }

    private void step() {
        // Euler step (small dt) -> smooth curves
        double dt = 0.02;

        // logistic growth + simple predation coupling
        double dA = rA * A * (1 - A / kA) - eatAB * A * B;
        double dB = rB * B * (1 - B / kB) + 0.6 * eatAB * A * B - eatBC * B * C;
        double dC = rC * C * (1 - C / kC) + 0.5 * eatBC * B * C;

        A = clamp(A + dA * dt);
        B = clamp(B + dB * dt);
        C = clamp(C + dC * dt);

        if (idx < STEPS) {
            aHist[idx] = A;
            bHist[idx] = B;
            cHist[idx] = C;
            idx++;
        } else {
            // scroll left
            System.arraycopy(aHist, 1, aHist, 0, STEPS - 1);
            System.arraycopy(bHist, 1, bHist, 0, STEPS - 1);
            System.arraycopy(cHist, 1, cHist, 0, STEPS - 1);
            aHist[STEPS - 1] = A;
            bHist[STEPS - 1] = B;
            cHist[STEPS - 1] = C;
        }

        t += dt;
        repaint();
    }

    private double clamp(double v) {
        if (Double.isNaN(v) || Double.isInfinite(v)) return 0.0;
        return Math.max(0.0, Math.min(1.0, v));
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        int w = getWidth(), h = getHeight();
        int left = 50, top = 40, right = 20, bottom = 60;
        int gw = w - left - right;
        int gh = h - top - bottom;

        g.setColor(Color.GRAY);
        g.drawRect(left, top, gw, gh);

        drawSeries(g, aHist, idx, left, top, gw, gh, Color.ORANGE);
        drawSeries(g, bHist, idx, left, top, gw, gh, Color.WHITE);
        drawSeries(g, cHist, idx, left, top, gw, gh, Color.LIGHT_GRAY);

        g.setColor(Color.GREEN);
        g.drawString("A (orange)  B (white)  C (gray)   t=" + String.format("%.2f", t), 10, 20);
    }

    private void drawSeries(Graphics2D g, double[] s, int n, int x0, int y0, int w, int h, Color col) {
        g.setColor(col);
        int len = Math.min(n, s.length);
        for (int i = 1; i < len; i++) {
            int x1 = x0 + (int) ((i - 1) * (w / (double) (s.length - 1)));
            int x2 = x0 + (int) (i * (w / (double) (s.length - 1)));
            int y1 = y0 + h - (int) (s[i - 1] * h);
            int y2 = y0 + h - (int) (s[i] * h);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("MultispeciesContinuousSimulation");
            f.add(new MultispeciesContinuousSimulation());
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
