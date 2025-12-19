import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Lab 09 - Step 10
 * Spawns many "rabbits" (balls) that bounce around the window using a Timer loop.
 */
public class RabbitsMover extends JPanel {
    static class RabbitBall {
        double x, y;
        double vx, vy;
        int r = 10;
        RabbitBall(double x, double y, double vx, double vy) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy;
        }
        void step(int w, int h, boolean bounce) {
            x += vx; y += vy;
            if (bounce) {
                if (x < 0 || x > w - 2*r) vx = -vx;
                if (y < 0 || y > h - 2*r) vy = -vy;
            }
        }
    }

    private final List<RabbitBall> rabbits = new ArrayList<>();
    private final Timer timer;
    private boolean bounceEnabled = true;

    public RabbitsMover() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        Random rand = new Random();
        for (int i = 0; i < 25; i++) {
            double x = rand.nextInt(760);
            double y = rand.nextInt(560);
            double vx = rand.nextDouble() * 6 - 3;
            double vy = rand.nextDouble() * 6 - 3;
            rabbits.add(new RabbitBall(x, y, vx, vy));
        }

        // 60 FPS-ish
        timer = new Timer(16, (ActionEvent e) -> {
            for (RabbitBall rb : rabbits) rb.step(getWidth(), getHeight(), bounceEnabled);
            repaint();
        });
        timer.start();

        // Toggle bounce with B key (helps demonstrate part 10e)
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_B) bounceEnabled = !bounceEnabled;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        for (RabbitBall rb : rabbits) {
            g.fillOval((int) rb.x, (int) rb.y, rb.r*2, rb.r*2);
        }
        g.setColor(Color.GREEN);
        g.drawString("Press B to toggle wall-bounce: " + bounceEnabled, 10, 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("RabbitsMover");
            RabbitsMover panel = new RabbitsMover();
            f.add(panel);
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}
