package userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import gamelogic.TicTacToeGame;
import gamelogic.Opponent;

/**
 * Swing-based user interface for the Tic Tac Toe game.
 * <p>
 * Renders a 3x3 grid of buttons, a status label, and a reset button.
 * Subscribes to {@link TicTacToeGame} events via {@link GameListener}
 * and updates UI accordingly. Supports Human vs Human and Human vs AI.
 * </p>
 *
 * @version 1.0
 * @author Danna Gomez
 */
public class SwingUI implements TicTacToeUI {

    private TicTacToeGame game;
    private boolean vsAI;
    private Opponent ai;
    private TicTacToeGame.Mark aiAs;

    private final JButton[] cells = new JButton[9];
    private final JLabel status = new JLabel(" ");
    private final JFrame frame = new JFrame("Tic-Tac-Toe");

    /**
     * Starts the Swing UI for the given game. Registers as a listener and
     * shows the frame on the EDT.
     *
     * @param game the game instance
     * @param vsAI true if playing against the AI
     * @param ai the AI opponent (ignored if {@code vsAI} is false)
     * @param aiPlaysAs the mark (X or O) used by the AI
     */
    @Override
    public void start(TicTacToeGame game, boolean vsAI, Opponent ai, TicTacToeGame.Mark aiPlaysAs) {
        this.game = game;
        this.vsAI = vsAI;
        this.ai = ai;
        this.aiAs = aiPlaysAs;

        SwingUtilities.invokeLater(() -> {
            initUi();
            game.addListener(this);
            updateFromBoard();
            frame.setVisible(true);
            maybeAIMove(); // in case AI starts
        });
    }

    /** Initializes frame, grid, status label, and reset control. */
    private void initUi() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        JPanel grid = new JPanel(new GridLayout(3, 3, 6, 6));
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 42);

        for (int i = 0; i < 9; i++) {
            final int idx = i;
            JButton btn = new JButton(" ");
            btn.setFont(f);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> onCell(idx));
            cells[i] = btn;
            grid.add(btn);
        }

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status.setFont(status.getFont().deriveFont(Font.BOLD));
        top.add(status);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton reset = new JButton(new AbstractAction("Reset") {
            @Override public void actionPerformed(ActionEvent e) {
                game.reset();
                maybeAIMove();
            }
        });
        bottom.add(reset);

        frame.add(top, BorderLayout.NORTH);
        frame.add(grid, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setSize(360, 420);
        frame.setLocationByPlatform(true);
    }

    /**
     * Handles a cell click from the user. If AI turn, ignores input.
     *
     * @param idx board index 0–8
     */
    private void onCell(int idx) {
        if (game.isGameOver()) return;
        if (vsAI && game.getCurrent() == aiAs) return; // wait for AI
        if (game.play(idx)) {
            maybeAIMove();
        }
    }

    /** If AI is enabled and it is the AI's turn, schedules an AI move. */
    private void maybeAIMove() {
        if (!vsAI || game.isGameOver()) return;
        if (game.getCurrent() != aiAs) return;
        // small delay for UX
        Timer t = new Timer(200, e -> {
            int move = ai.chooseMove(game, aiAs);
            game.play(move);
        });
        t.setRepeats(false);
        t.start();
    }

    /** Updates button labels, enable states, and status text from game state. */
    private void updateFromBoard() {
        var b = game.getBoard();
        for (int i = 0; i < 9; i++) {
            String text = switch (b[i]) {
                case X -> "X";
                case O -> "O";
                default -> " ";
            };
            cells[i].setText(text);
            cells[i].setEnabled(!game.isGameOver() && b[i] == TicTacToeGame.Mark.EMPTY);
        }
        if (game.isGameOver()) {
            if (game.getWinner() == TicTacToeGame.Mark.EMPTY) {
                status.setText("Draw. Click Reset.");
            } else {
                status.setText("Winner: " + game.getWinner() + ". Click Reset.");
            }
        } else {
            status.setText("Turn: " + game.getCurrent() + (vsAI ? (", AI as " + aiAs) : ""));
        }
    }

    // ------------------------------------------------------------
    // GameListener implementation
    // ------------------------------------------------------------

    /** After any move, refresh the board and status. */
    @Override public void onMove(int index, TicTacToeGame.Mark who) { updateFromBoard(); }

    /** At game end, refresh the board and status. */
    @Override public void onGameOver(TicTacToeGame.Mark winner) { updateFromBoard(); }

    /** After reset, refresh the board and status. */
    @Override public void onReset(TicTacToeGame.Mark starting) { updateFromBoard(); }
}
