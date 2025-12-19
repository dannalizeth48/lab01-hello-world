import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Lab 09 - Step 15
 * Refactored Ecosystem panel (separate class files).
 *
 * Controls:
 * - Arrow keys move wolf
 * - Press I to toggle inverted controls
 */
public class Ecosystem extends JPanel {
    private final int WIDTH = 800, HEIGHT = 600;

    private final InputHandler input = new InputHandler();

    private final Wolf wolf = new Wolf(360, 260);
    private final List<Rabbit> rabbits = new ArrayList<>();
    private final List<Carrot> carrots = new ArrayList<>();
    private final List<Fungus> fungi = new ArrayList<>();

    private BufferedImage wolfSheet;
    private BufferedImage rabbitSheet;

    private final Timer timer;

    public Ecosystem() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(input);

        // spawn initial rabbits and fungus
        for (int i = 0; i < 10; i++) rabbits.add(new Rabbit(50 + i * 60, 100 + (i % 3) * 80));
        fungi.add(new Fungus(100, 450));

        loadSprites();

        // main loop ~33 FPS
        timer = new Timer(30, (ActionEvent e) -> tick());
        timer.start();

        // carrot spawner (uses random.org hex when available; falls back to pseudorandom)
        new Thread(new CarrotSpawner(), "CarrotSpawner").start();

        // local key toggle (I) for inverted controls
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_I) {
                    input.setInvertControls(true);
                }
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_O) {
                    input.setInvertControls(false);
                }
            }
        });
    }

    private void loadSprites() {
        try {
            wolfSheet = ImageIO.read(new java.io.File("resources/wolfSheet.png"));
        } catch (Exception e) {
            wolfSheet = null;
        }
        try {
            rabbitSheet = ImageIO.read(new java.io.File("resources/rabbitSheet.png"));
        } catch (Exception e) {
            rabbitSheet = null;
        }
    }

    private void tick() {
        wolf.move(input, WIDTH, HEIGHT, 7); // faster wolf (matches Step 11d idea)
        wolf.animate();

        for (Rabbit r : rabbits) {
            r.move(WIDTH, HEIGHT);
            r.animate();
        }

        for (Fungus f : fungi) f.move(WIDTH, HEIGHT);

        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        // Wolf eats rabbits
        Iterator<Rabbit> rit = rabbits.iterator();
        while (rit.hasNext()) {
            Rabbit r = rit.next();
            if (wolf.bounds().intersects(r.bounds())) {
                rit.remove();
            }
        }

        // Rabbits eat carrots
        Iterator<Carrot> cit = carrots.iterator();
        while (cit.hasNext()) {
            Carrot c = cit.next();
            boolean eaten = false;
            for (Rabbit r : rabbits) {
                if (r.bounds().intersects(c.bounds())) {
                    eaten = true;
                    break;
                }
            }
            if (eaten) cit.remove();
        }

        // Fungus eats carrots and can "damage" wolf by resetting its position
        Iterator<Fungus> fit = fungi.iterator();
        while (fit.hasNext()) {
            Fungus f = fit.next();

            if (f.bounds().intersects(wolf.bounds())) {
                // wolf "hit" fungus -> reset position
                wolf.x = 360; wolf.y = 260;
            }

            // fungus eats carrots too
            Iterator<Carrot> cit2 = carrots.iterator();
            while (cit2.hasNext()) {
                Carrot c = cit2.next();
                if (f.bounds().intersects(c.bounds())) {
                    cit2.remove();
                }
            }
        }

        // if too few rabbits, spawn more to keep simulation going
        if (rabbits.size() < 4) {
            rabbits.add(new Rabbit(50, 50));
        }
        // occasionally spawn extra fungus
        if (fungi.size() < 2 && new Random().nextDouble() < 0.005) {
            fungi.add(new Fungus(700, 500));
        }
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        // carrots
        for (Carrot c : carrots) c.draw(g);

        // fungus
        for (Fungus f : fungi) f.draw(g);

        // rabbits
        for (Rabbit r : rabbits) r.draw(g, rabbitSheet);

        // wolf
        wolf.draw(g, wolfSheet);

        g.setColor(Color.GREEN);
        g.drawString("Rabbits: " + rabbits.size() + "  Carrots: " + carrots.size() + "  Fungus: " + fungi.size(), 10, 20);
        g.drawString("Arrow keys move. Press I = invert controls ON, O = OFF", 10, 40);
    }

    private class CarrotSpawner implements Runnable {
        private final Random fallback = new Random();

        @Override
        public void run() {
            while (true) {
                try {
                    int spawnX, spawnY;

                    String hex = fetchRandomHex8();
                    if (hex != null && hex.length() >= 8) {
                        hex = hex.replaceAll("\\s+", "");
                        int xSeed = Integer.parseInt(hex.substring(0, 4), 16);
                        int ySeed = Integer.parseInt(hex.substring(4, 8), 16);
                        spawnX = xSeed % (WIDTH - 12);
                        spawnY = ySeed % (HEIGHT - 12);
                    } else {
                        spawnX = fallback.nextInt(WIDTH - 12);
                        spawnY = fallback.nextInt(HEIGHT - 12);
                    }

                    int fx = spawnX;
                    int fy = spawnY;
                    SwingUtilities.invokeLater(() -> carrots.add(new Carrot(fx, fy)));

                } catch (Exception ignored) {
                    // ignore and keep spawning
                }

                try { Thread.sleep(1200); } catch (InterruptedException e) { return; }
            }
        }

        private String fetchRandomHex8() {
            try {
                URL url = new URL("https://www.random.org/cgi-bin/randbyte?nbytes=4&format=h");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    return br.readLine();
                }
            } catch (Exception e) {
                return null;
            }
        }
    }
}
