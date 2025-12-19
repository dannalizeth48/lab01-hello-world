import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Rabbit creature (autonomous).
 */
public class Rabbit {
    public int x, y;
    public int size = 40;
    private double dx, dy;
    private int frame = 0;

    public Rabbit(int x, int y) {
        this.x = x;
        this.y = y;
        Random rand = new Random();
        dx = rand.nextDouble() * 4 - 2;
        dy = rand.nextDouble() * 4 - 2;
    }

    public void move(int w, int h) {
        x += (int) dx;
        y += (int) dy;

        if (x < 0 || x > w - size) dx = -dx;
        if (y < 0 || y > h - size) dy = -dy;

        x = Math.max(0, Math.min(w - size, x));
        y = Math.max(0, Math.min(h - size, y));
    }

    public void animate() {
        frame = (frame + 1) % 4;
    }

    public void draw(Graphics2D g, BufferedImage sheet) {
        if (sheet == null) {
            g.setColor(Color.WHITE);
            g.fillOval(x, y, size, size);
            return;
        }
        int fw = 64, fh = 64;
        BufferedImage f = sheet.getSubimage(frame * fw, 0, fw, fh);
        g.drawImage(f, x, y, size, size, null);
    }

    public Rectangle bounds() {
        return new Rectangle(x, y, size, size);
    }
}
