/**
 * Interface for user interface components of the Tic Tac Toe game.
 * 
 * Implementations of this interface (such as CLIUI and SwingUI)
 * are responsible for:
 * <ul>
 *   <li>Displaying the game state to the user</li>
 *   <li>Handling user input</li>
 *   <li>Reacting to game events through the GameListener callbacks</li>
 * </ul>
 *
 * Because it extends GameListener, any UI class receives notifications
 * when moves occur, when the game resets, and when the game ends.
 *
 * @version 1.0
 * @author Danna Gomez
 */
public interface TicTacToeUI extends GameListener {

    /**
     * Starts the user interface for the given game.
     * Implementations must register themselves as listeners for updates.
     *
     * @param game the TicTacToeGame instance to control
     * @param vsAI true if the user is playing against the AI, false for two-player mode
     * @param ai the AI opponent object (ignored if vsAI is false)
     * @param aiPlaysAs the mark (X or O) that the AI will use
     */
    void start(TicTacToeGame game, boolean vsAI, Opponent ai, TicTacToeGame.Mark aiPlaysAs);
}
