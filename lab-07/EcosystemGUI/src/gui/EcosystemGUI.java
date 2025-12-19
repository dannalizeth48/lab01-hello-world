package gui;

import ecosystem.EcosystemWorld;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import javax.swing.*;

public class EcosystemGUI {

    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int PLAY_DELAY_MS = 400;

    // points to: lab-07/EcosystemGUI/initial_state.txt
    private static final Path INITIAL_FILE = Path.of("..", "initial_state.txt");

    private final EcosystemWorld world = new EcosystemWorld(ROWS, COLS);

    private final JTextArea sidebar = new JTextArea();
    private final JTextArea logArea = new JTextArea();

    private final JButton[][] gridButtons = new JButton[ROWS][COLS];

    private Timer playTimer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcosystemGUI().start());
    }

    private void start() {
        JFrame frame = new JFrame("EcosystemGUI - Lab 07");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(buildTopControls(), BorderLayout.NORTH);
        frame.add(buildGridPanel(), BorderLayout.CENTER);
        frame.add(buildSidebar(), BorderLayout.EAST);
        frame.add(buildLogPanel(), BorderLayout.SOUTH);

        try {
            world.loadInitialFromFile(INITIAL_FILE);
            refreshGrid();
            log("Loaded initial state from: " + INITIAL_FILE.toString());
        } catch (Exception ex) {
            log("ERROR loading initial state: " + ex.getMessage());
        }

        updateSidebar(0, 0);
        log("GUI started.");

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel buildTopControls() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton stepBtn = new JButton("Step");
        JButton playBtn = new JButton("Play");
        JButton stopBtn = new JButton("Stop");
        JButton resetBtn = new JButton("Reset");

        JButton loadBtn = new JButton("Load Initial");
        JButton saveInitialBtn = new JButton("Save Initial");

        stepBtn.addActionListener(e -> doStep());
        playBtn.addActionListener(e -> startPlay());
        stopBtn.addActionListener(e -> stopPlay());
        resetBtn.addActionListener(e -> doReset());

        loadBtn.addActionListener(e -> doLoadInitial());
        saveInitialBtn.addActionListener(e -> doSaveInitial());

        top.add(stepBtn);
        top.add(playBtn);
        top.add(stopBtn);
        top.add(resetBtn);
        top.add(loadBtn);
        top.add(saveInitialBtn);

        return top;
    }

    private JPanel buildGridPanel() {
        JPanel grid = new JPanel(new GridLayout(ROWS, COLS, 2, 2));
        grid.setBorder(BorderFactory.createTitledBorder("Grid"));

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(35, 35));
                final int rr = r;
                final int cc = c;

                b.addActionListener((ActionEvent e) -> updateSidebar(rr, cc));

                gridButtons[r][c] = b;
                grid.add(b);
            }
        }
        return grid;
    }

    private JScrollPane buildSidebar() {
        sidebar.setEditable(false);
        sidebar.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        sidebar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        JScrollPane sp = new JScrollPane(sidebar);
        sp.setPreferredSize(new Dimension(260, 0));
        sp.setBorder(BorderFactory.createTitledBorder("Selected Cell"));
        return sp;
    }

    private JScrollPane buildLogPanel() {
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(logArea);
        sp.setPreferredSize(new Dimension(0, 160));
        sp.setBorder(BorderFactory.createTitledBorder("Log"));
        return sp;
    }

    private void updateSidebar(int r, int c) {
        sidebar.setText(world.getCellSummary(r, c));
        log("Selected cell: (" + r + "," + c + ")");
    }

    private void doStep() {
        world.takeTurn();
        log("Turn advanced. Turn=" + world.getTurn());
        refreshGrid();
    }

    private void startPlay() {
        if (playTimer != null && playTimer.isRunning()) return;

        playTimer = new Timer(PLAY_DELAY_MS, e -> doStep());
        playTimer.start();
        log("Play started.");
    }

    private void stopPlay() {
        if (playTimer != null) {
            playTimer.stop();
            log("Play stopped.");
        }
    }

    private void doReset() {
        stopPlay();
        world.resetToInitial();
        log("World reset to initial.");
        refreshGrid();
        updateSidebar(0, 0);
    }

    private void doLoadInitial() {
        stopPlay();
        try {
            world.loadInitialFromFile(INITIAL_FILE);
            log("Initial state loaded.");
            refreshGrid();
            updateSidebar(0, 0);
        } catch (Exception ex) {
            log("ERROR loading initial: " + ex.getMessage());
        }
    }

    private void doSaveInitial() {
        try {
            world.saveInitialToFile(INITIAL_FILE);
            log("Initial state saved.");
        } catch (Exception ex) {
            log("ERROR saving initial: " + ex.getMessage());
        }
    }

    private void refreshGrid() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                gridButtons[r][c].setText(String.valueOf(world.getCellSymbol(r, c)));
            }
        }
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}
