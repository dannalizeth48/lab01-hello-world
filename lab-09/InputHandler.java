import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for the Ecosystem.
 */
public class InputHandler implements KeyListener {
    private boolean up, down, left, right;

    // Lab requires inverted controls (Step 11d); set true to invert.
    private boolean invertControls = false;

    public void setInvertControls(boolean invert) {
        this.invertControls = invert;
    }

    public boolean isUp() { return up; }
    public boolean isDown() { return down; }
    public boolean isLeft() { return left; }
    public boolean isRight() { return right; }

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!invertControls) {
            if (code == KeyEvent.VK_UP) up = true;
            if (code == KeyEvent.VK_DOWN) down = true;
            if (code == KeyEvent.VK_LEFT) left = true;
            if (code == KeyEvent.VK_RIGHT) right = true;
        } else {
            // inverted
            if (code == KeyEvent.VK_UP) down = true;
            if (code == KeyEvent.VK_DOWN) up = true;
            if (code == KeyEvent.VK_LEFT) right = true;
            if (code == KeyEvent.VK_RIGHT) left = true;
        }
    }

    @Override public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (!invertControls) {
            if (code == KeyEvent.VK_UP) up = false;
            if (code == KeyEvent.VK_DOWN) down = false;
            if (code == KeyEvent.VK_LEFT) left = false;
            if (code == KeyEvent.VK_RIGHT) right = false;
        } else {
            if (code == KeyEvent.VK_UP) down = false;
            if (code == KeyEvent.VK_DOWN) up = false;
            if (code == KeyEvent.VK_LEFT) right = false;
            if (code == KeyEvent.VK_RIGHT) left = false;
        }
    }
}
