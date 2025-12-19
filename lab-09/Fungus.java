import java.awt.*;
import java.util.Random;

/**
 * Lab 09 - Step 15f
 * Fungus eats wolves and carrots (hazard).
 */
public class Fungus {
    public int x, y;
    public int size = 26;
    private double dx, dy;

    public Fungus(int x, int y) {
        this.x = x;
        this.y = y;
        Random r = new Random();
        dx = r.nextDouble() * 3 - 1.5;
        dy = r.nextDouble() * 3 - 1.5;
    }

    public void move(int w, int h) {
        x += (int) dx;
        y += (int) dy;
        if (x < 0 || x > w - size) dx = -dx;
        if (y < 0 || y > h - size) dy = -dy;
        x = Math.max(0, Math.min(w - size, x));
        y = Math.max(0, Math.min(h - size, y));
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(140, 0, 180));
        g.fillOval(x, y, size, size);
    }

    public Rectangle bounds() {
        return new Rectangle(x, y, size, size);
    }
}
