import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Lab 09 - Step 12
 * Loads an image file and paints it.
 */
public class SpriteLoader extends JPanel {
    private BufferedImage img;

    public SpriteLoader() {
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
            g.drawImage(img, 20, 20, null);
        } else {
            g.setColor(Color.RED);
            g.drawString("Could not load resources/spriteSheet.png", 20, 40);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("SpriteLoader");
            f.add(new SpriteLoader());
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
