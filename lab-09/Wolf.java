import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Wolf creature (player controlled).
 */
public class Wolf {
    public int x, y;
    public int size = 48;

    private int frame = 0;

    public Wolf(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(InputHandler input, int w, int h, int speed) {
        if (input.isUp()) y -= speed;
        if (input.isDown()) y += speed;
        if (input.isLeft()) x -= speed;
        if (input.isRight()) x += speed;

        x = Math.max(0, Math.min(w - size, x));
        y = Math.max(0, Math.min(h - size, y));
    }

    public void animate() {
        frame = (frame + 1) % 4;
    }

    public void draw(Graphics2D g, BufferedImage sheet) {
        if (sheet == null) {
            g.setColor(Color.DARK_GRAY);
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
