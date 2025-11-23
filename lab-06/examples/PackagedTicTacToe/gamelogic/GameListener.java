package gamelogic;

/**
 * Listener interface used to receive notifications from the TicTacToeGame.
 * Both CLI and Swing user interface classes implement this interface so they
 * can respond whenever the game changes state.
 *
 * Events that are reported:
 * <ul>
 *   <li>A move is made (onMove)</li>
 *   <li>The game ends (onGameOver)</li>
 *   <li>The game is reset (onReset)</li>
 * </ul>
 *
 * This design keeps the game logic independent from the UI.
 * 
 * @version 1.0
 * @author Danna Gomez
 */
public interface GameListener {

    /**
     * Called when a valid move is played on the board.
     *
     * @param index the board position where the move occurred (0–8)
     * @param who the mark (X or O) that played the move
     */
    void onMove(int index, TicTacToeGame.Mark who);

    /**
     * Called when the game finishes.
     *
     * @param winner X or O if there is a winner, or EMPTY if the game is a draw
     */
    void onGameOver(TicTacToeGame.Mark winner);

    /**
     * Called after the game is reset to its initial empty board.
     *
     * @param starting the mark (X or O) who begins the new game
     */
    void onReset(TicTacToeGame.Mark starting);
}
