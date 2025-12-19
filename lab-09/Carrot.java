import java.awt.*;

/**
 * Carrot food item.
 */
public class Carrot {
    public int x, y;
    public int size = 12;

    public Carrot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, size, size);
    }

    public Rectangle bounds() {
        return new Rectangle(x, y, size, size);
    }
}
