package userinterface;

import java.util.Scanner;

import gamelogic.TicTacToeGame;
import gamelogic.Opponent;

/**
 * Command-line interface (CLI) implementation for the Tic Tac Toe game.
 * 
 * This class interacts with the user through text-based input and output.
 * It supports:
 * <ul>
 *   <li>Human vs Human gameplay</li>
 *   <li>Human vs AI gameplay</li>
 *   <li>Real-time updates via GameListener</li>
 * </ul>
 *
 * The CLI prints the board after each move and prompts the user for input
 * until the game ends.
 *
 * @version 1.0
 * @author Danna Gomez
 */
public class CLIUI implements TicTacToeUI {

    private TicTacToeGame game;
    private boolean vsAI;
    private Opponent ai;
    private TicTacToeGame.Mark aiAs;
    private final Scanner sc = new Scanner(System.in);

    /**
     * Starts the CLI interface for a TicTacToeGame session.
     * Registers itself as a GameListener and manages user turns
     * or AI turns depending on configuration.
     *
     * @param game the TicTacToeGame instance
     * @param vsAI true if playing against the AI opponent
     * @param ai the Opponent implementation (AI)
     * @param aiPlaysAs the mark (X or O) used by the AI
     */
    @Override
    public void start(TicTacToeGame game, boolean vsAI, Opponent ai, TicTacToeGame.Mark aiPlaysAs) {
        this.game = game;
        this.vsAI = vsAI;
        this.ai = ai;
        this.aiAs = aiPlaysAs;

        game.addListener(this);
        draw(game.getBoard());

        while (!game.isGameOver()) {
            if (vsAI && game.getCurrent() == aiAs) {
                int move = ai.chooseMove(game, aiAs);
                game.play(move);
            } else {
                int move = promptMove();
                if (!game.play(move)) {
                    System.out.println("Illegal move. Try again.");
                }
            }
        }
        // GameListener.onGameOver handles final message
    }

    /**
     * Prompts the human player for a move.
     *
     * @return an integer from 0–8 representing a board position
     */
    private int promptMove() {
        while (true) {
            System.out.print("Player " + game.getCurrent() + " move (1-9): ");
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s) - 1;
                if (v >= 0 && v <= 8) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Enter a number 1..9.");
        }
    }

    /**
     * Draws the board to the console.
     *
     * @param b the 9-element board array
     */
    private void draw(TicTacToeGame.Mark[] b) {
        System.out.println();
        for (int r = 0; r < 3; r++) {
            int i = r * 3;
            System.out.printf(" %s | %s | %s %n",
                    sym(b[i]), sym(b[i+1]), sym(b[i+2]));
            if (r < 2) System.out.println("---+---+---");
        }
        System.out.println();
    }

    /**
     * Converts a Mark value into its printable symbol.
     *
     * @param m X, O, or EMPTY
     * @return "X", "O", or " "
     */
    private String sym(TicTacToeGame.Mark m) {
        return switch (m) {
            case X -> "X";
            case O -> "O";
            default -> " ";
        };
    }

    // ------------------------------------------------------------
    // GameListener implementation
    // ------------------------------------------------------------

    /** Redraws the board after each move. */
    @Override
    public void onMove(int index, TicTacToeGame.Mark who) {
        draw(game.getBoard());
    }

    /** Prints the game result at the end. */
    @Override
    public void onGameOver(TicTacToeGame.Mark winner) {
        if (winner == TicTacToeGame.Mark.EMPTY) {
            System.out.println("Draw!");
        } else {
            System.out.println("Winner: " + winner);
        }
    }

    /** Announces that a new game has begun. */
    @Override
    public void onReset(TicTacToeGame.Mark starting) {
        System.out.println("=== New Game. " + starting + " starts. ===");
    }
}

