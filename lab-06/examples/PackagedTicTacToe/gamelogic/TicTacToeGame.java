package gamelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Core game logic for an Object-Oriented Tic Tac Toe implementation.
 * Handles board state, turns, legal moves, winner detection,
 * listeners for UI updates, and game progression.
 *
 * Board positions are indexed 0–8:
 *  0 | 1 | 2
 *  3 | 4 | 5
 *  6 | 7 | 8
 *
 * X always starts the game.
 * A game ends when there is a winner or no legal moves remain (draw).
 *
 * @author Danna Gomez
 * @version 1.0
 */
public class TicTacToeGame {

    /**
     * Represents the content of a board cell.
     * X or O for players, EMPTY for unoccupied tiles.
     */
    public enum Mark { X, O, EMPTY }

    private final Mark[] board = new Mark[9];
    private Mark current = Mark.X;
    private boolean gameOver = false;
    private Mark winner = Mark.EMPTY;
    private final List<GameListener> listeners = new ArrayList<>();

    /** Creates a new game and resets all values. */
    public TicTacToeGame() { reset(); }

    /** 
     * Resets the board to its initial empty state.
     * X always begins a new game.
     */
    public void reset() {
        Arrays.fill(board, Mark.EMPTY);
        current = Mark.X;
        winner = Mark.EMPTY;
        gameOver = false;
        fireReset(current);
    }

    /**
     * Returns a defensive copy of the board.
     *
     * @return an array of 9 elements representing the board
     */
    public Mark[] getBoard() { return board.clone(); }

    /**
     * @return the mark (X or O) whose turn it is to play
     */
    public Mark getCurrent() { return current; }

    /**
     * @return true if the game has ended, false otherwise
     */
    public boolean isGameOver() { return gameOver; }

    /**
     * @return the winning mark; EMPTY means no winner or a draw
     */
    public Mark getWinner() { return winner; }

    /**
     * Attempts to play a move in the given position.
     *
     * @param index a number from 0–8 representing a board position
     * @return true if the move is valid and accepted, false if illegal or game over
     */
    public boolean play(int index) {
        if (gameOver || index < 0 || index > 8 || board[index] != Mark.EMPTY) return false;

        board[index] = current;
        fireMove(index, current);

        Mark w = computeWinner();
        if (w != Mark.EMPTY) {
            winner = w;
            gameOver = true;
            fireGameOver(winner);
        } else if (isFull()) {
            gameOver = true;
            winner = Mark.EMPTY; // draw
            fireGameOver(Mark.EMPTY);
        } else {
            current = (current == Mark.X ? Mark.O : Mark.X);
        }
        return true;
    }

    /**
     * Computes a list of valid moves that can still be played.
     *
     * @return list of board indices that are EMPTY
     */
    public List<Integer> legalMoves() {
        List<Integer> moves = new ArrayList<>();
        if (gameOver) return moves;
        for (int i = 0; i < 9; i++) if (board[i] == Mark.EMPTY) moves.add(i);
        return moves;
    }

    /**
     * Registers a listener to receive game update events.
     *
     * @param l a GameListener instance
     */
    public void addListener(GameListener l) { listeners.add(Objects.requireNonNull(l)); }

    /**
     * Removes a previously added listener.
     *
     * @param l a GameListener instance
     */
    public void removeListener(GameListener l) { listeners.remove(l); }

    /** Notifies listeners when a move is played. */
    private void fireMove(int idx, Mark who) { for (var l : listeners) l.onMove(idx, who); }

    /** Notifies listeners when the game ends. */
    private void fireGameOver(Mark winner) { for (var l : listeners) l.onGameOver(winner); }

    /** Notifies listeners when the game resets. */
    private void fireReset(Mark starting) { for (var l : listeners) l.onReset(starting); }

    /** @return true if the board is full */
    private boolean isFull() {
        for (Mark m : board) if (m == Mark.EMPTY) return false;
        return true;
    }

    /** All winning line combinations. */
    private static final int[][] LINES = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    /**
     * Computes whether a winner exists.
     *
     * @return X, O, or EMPTY if there is no winner yet
     */
    private Mark computeWinner() {
        for (int[] line : LINES) {
            Mark a = board[line[0]], b = board[line[1]], c = board[line[2]];
            if (a != Mark.EMPTY && a == b && b == c) return a;
        }
        return Mark.EMPTY;
    }
}
