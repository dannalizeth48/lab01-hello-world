import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Lab 09 - Step 12d
 * Loads a different file name (edit this to test "resources/spriteSheet.png").
 */
public class SpriteLoader2 extends JPanel {
    private BufferedImage img;

    public SpriteLoader2() {
        try {
            img = ImageIO.read(new File("resources/spriteSheet.png"));
        } catch (Exception e) {
            img = null;
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(500, 200));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            // draw just first frame 64x64
            g.drawImage(img.getSubimage(0, 0, 64, 64), 40, 40, 128, 128, null);
        } else {
            g.setColor(Color.RED);
            g.drawString("Could not load resources/spriteSheet.png", 20, 40);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("SpriteLoader2");
            f.add(new SpriteLoader2());
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
