import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Lab 09 - Step 11
 * Moves a single "wolf" using arrow keys; demonstrates timer + keyboard input.
 */
public class WolfMover extends JPanel implements KeyListener {
    private int x = 200, y = 200;
    private int size = 40;
    private int speed = 6;

    private boolean up, down, left, right;
    private final Timer timer;

    public WolfMover() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, (ActionEvent e) -> {
            if (up) y -= speed;
            if (down) y += speed;
            if (left) x -= speed;
            if (right) x += speed;
            x = Math.max(0, Math.min(getWidth() - size, x));
            y = Math.max(0, Math.min(getHeight() - size, y));
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.MAGENTA);
        g.fillOval(x, y, size, size);
        g.setColor(Color.GREEN);
        g.drawString("Arrow keys to move. (Controls can be inverted by editing key mapping.)", 10, 20);
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_DOWN -> down = true;
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
        }
    }
    @Override public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_DOWN -> down = false;
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("WolfMover");
            WolfMover panel = new WolfMover();
            f.add(panel);
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}
