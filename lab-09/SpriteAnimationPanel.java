import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Lab 09 - Step 12e
 * Animates a spritesheet using a Swing Timer.
 */
public class SpriteAnimationPanel extends JPanel {
    private BufferedImage sheet;
    private int frame = 0;
    private final int frameW = 64;
    private final int frameH = 64;

    public SpriteAnimationPanel() {
        setPreferredSize(new Dimension(500, 250));
        setBackground(Color.BLACK);

        try {
            sheet = ImageIO.read(new File("resources/spriteSheet.png"));
        } catch (Exception e) {
            sheet = null;
            e.printStackTrace();
        }

        Timer t = new Timer(120, (ActionEvent e) -> {
            frame = (frame + 1) % 4;
            repaint();
        });
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sheet == null) {
            g.setColor(Color.RED);
            g.drawString("Could not load resources/spriteSheet.png", 20, 40);
            return;
        }

        BufferedImage f = sheet.getSubimage(frame * frameW, 0, frameW, frameH);
        g.drawImage(f, 60, 60, 160, 160, null);
        g.setColor(Color.GREEN);
        g.drawString("Frame: " + frame, 10, 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("SpriteAnimationPanel");
            f.add(new SpriteAnimationPanel());
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
