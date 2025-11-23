package gamelogic;

/**
 * Strategy interface for an automated Tic Tac Toe opponent.
 * Implementations choose a legal move for the given game state.
 *
 * @version 1.0
 * @author Danna Gomez
 */
public interface Opponent {

    /**
     * Chooses a board position to play (0–8).
     * Assumes it is {@code me}'s turn and the game is not over.
     *
     * @param game current immutable-facing game state
     * @param me   the mark used by the opponent (X or O)
     * @return an index 0–8 that is a legal move
     */
    int chooseMove(TicTacToeGame game, TicTacToeGame.Mark me);
}
