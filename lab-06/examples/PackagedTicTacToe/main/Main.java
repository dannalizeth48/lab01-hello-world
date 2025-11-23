package main;

import gamelogic.TicTacToeGame;
import gamelogic.Opponent;
import gamelogic.MinimaxOpponent;   // <-- NUEVO
import userinterface.TicTacToeUI;   // <-- NUEVO
import userinterface.CLIUI;
import userinterface.SwingUI;


/**
 * Entry point for the Object-Oriented Tic Tac Toe project.
 * Supports CLI with --cli and Swing with --swing.
 * @author Danna Gomez
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        // Config (you can change these or make them flags)
        boolean useSwing = argsContain(args, "--swing");
        boolean useCLI   = argsContain(args, "--cli") || !useSwing;
        boolean vsAI     = argsContain(args, "--ai");          // human vs AI if present
        boolean aiFirst  = argsContain(args, "--ai-first");     // AI plays X if present

        TicTacToeGame game = new TicTacToeGame();
        Opponent ai = new MinimaxOpponent();
        TicTacToeGame.Mark aiAs = aiFirst ? TicTacToeGame.Mark.X : TicTacToeGame.Mark.O;

        if (useSwing) {
            TicTacToeUI ui = new SwingUI();
            ui.start(game, vsAI, ai, aiAs);
        } else if (useCLI) {
            TicTacToeUI ui = new CLIUI();
            ui.start(game, vsAI, ai, aiAs);
        }
    }

    /**
     * Checks whether a flag is present in the command-line arguments.
     * @param args The array of command-line arguments.
     * @param flag The flag to search for (e.g., "--cli").
     * @return true if the flag is present, otherwise false.
     */
    private static boolean argsContain(String[] args, String flag) {
        if (args == null) return false;
        for (String a : args) if (flag.equalsIgnoreCase(a)) return true;
        return false;
    }
}
