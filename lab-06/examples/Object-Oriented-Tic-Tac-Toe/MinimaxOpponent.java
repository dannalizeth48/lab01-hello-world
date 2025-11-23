import java.util.List;

/**
 * Minimax-based opponent with alpha-beta pruning.
 * Includes quick heuristics (center > corner) to speed early choices,
 * then falls back to full search to guarantee optimal play.
 *
 * @version 1.0
 * author Danna Gomez
 */
public class MinimaxOpponent implements Opponent {

    /**
     * Picks a move using small heuristics first, then full minimax.
     *
     * @param game current game
     * @param me   mark used by this AI
     * @return index 0–8
     */
    @Override
    public int chooseMove(TicTacToeGame game, TicTacToeGame.Mark me) {
        TicTacToeGame.Mark opp = (me == TicTacToeGame.Mark.X ? TicTacToeGame.Mark.O
                                                             : TicTacToeGame.Mark.X);

        List<Integer> moves = game.legalMoves();

        // Quick heuristics to reduce search:
        if (moves.contains(4)) return 4;                 // center
        int[] corners = {0, 2, 6, 8};
        for (int c : corners) {
            if (moves.contains(c)) return c;             // any corner
        }

        // Fall back to full minimax search.
        return bestByMinimax(game, me, opp);
    }

    /**
     * Explores every legal move with minimax and returns the best scoring move.
     */
    private int bestByMinimax(TicTacToeGame game,
                              TicTacToeGame.Mark me,
                              TicTacToeGame.Mark opp) {

        int bestScore = Integer.MIN_VALUE;
        int bestMove  = -1;

        for (int move : game.legalMoves()) {
            // simulate
            Sim s = new Sim(game);
            s.play(move);
            int score = minimax(s, /*maximizing*/ false, me, opp,
                                Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (score > bestScore) {
                bestScore = score;
                bestMove  = move;
            }
        }
        return bestMove;
    }

    /**
     * Minimax with alpha-beta pruning on a lightweight simulator.
     *
     * @param s          simulated state
     * @param maximizing true if it's 'me' to move; false if 'opp'
     * @param me         AI's mark
     * @param opp        opponent's mark
     * @param alpha      best already guaranteed for maximizer
     * @param beta       best already guaranteed for minimizer
     * @return score from the perspective of {@code me}
     */
    private int minimax(Sim s, boolean maximizing,
                        TicTacToeGame.Mark me, TicTacToeGame.Mark opp,
                        int alpha, int beta) {

        if (s.isOver()) {
            TicTacToeGame.Mark w = s.winner();
            if (w == me)  return +10;
            if (w == opp) return -10;
            return 0; // draw
        }

        if (maximizing) {
            int best = Integer.MIN_VALUE;
            for (int m : s.legalMoves()) {
                Sim nxt = s.copy();
                nxt.play(m);
                int val = minimax(nxt, false, me, opp, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, val);
                if (beta <= alpha) break; // prune
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int m : s.legalMoves()) {
                Sim nxt = s.copy();
                nxt.play(m);
                int val = minimax(nxt, true, me, opp, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, val);
                if (beta <= alpha) break; // prune
            }
            return best;
        }
    }

    /**
     * Lightweight simulator view of the game for search.
     * Copies board/turn so minimax does not mutate the real game.
     */
    private static class Sim {
        private final TicTacToeGame.Mark[] b = new TicTacToeGame.Mark[9];
        private TicTacToeGame.Mark turn;
        private boolean over = false;
        private TicTacToeGame.Mark win = TicTacToeGame.Mark.EMPTY;

        /** Snapshot from a live game. */
        Sim(TicTacToeGame g) {
            var src = g.getBoard();
            System.arraycopy(src, 0, b, 0, 9);
            turn = g.getCurrent();
            over = g.isGameOver();
            win  = g.getWinner();
        }

        /** Internal constructor for copy. Performs array copy. */
        Sim(TicTacToeGame.Mark[] b, TicTacToeGame.Mark turn,
            boolean over, TicTacToeGame.Mark win) {
            System.arraycopy(b, 0, this.b, 0, 9);
            this.turn = turn;
            this.over = over;
            this.win  = win;
        }

        /** Deep copy for branching. */
        Sim copy() { return new Sim(b, turn, over, win); }

        boolean isOver()               { return over; }
        TicTacToeGame.Mark winner()    { return win; }

        List<Integer> legalMoves() {
            java.util.ArrayList<Integer> ms = new java.util.ArrayList<>();
            if (over) return ms;
            for (int i = 0; i < 9; i++) if (b[i] == TicTacToeGame.Mark.EMPTY) ms.add(i);
            return ms;
        }

        /**
         * Attempts to play a move in the simulator.
         * Updates winner/over, and flips turn if game continues.
         */
        boolean play(int idx) {
            if (over || idx < 0 || idx > 8 || b[idx] != TicTacToeGame.Mark.EMPTY) return false;
            b[idx] = turn;

            // Winner check
            int[][] L = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
            };
            for (int[] line : L) {
                TicTacToeGame.Mark a = b[line[0]];
                TicTacToeGame.Mark c = b[line[1]];
                TicTacToeGame.Mark d = b[line[2]];
                if (a != TicTacToeGame.Mark.EMPTY && a == c && c == d) {
                    win = a;
                    over = true;
                    return true;
                }
            }

            // Draw check
            boolean full = true;
            for (TicTacToeGame.Mark m : b) {
                if (m == TicTacToeGame.Mark.EMPTY) { full = false; break; }
            }
            if (full) {
                over = true;
                win = TicTacToeGame.Mark.EMPTY;
                return true;
            }

            // Next turn
            turn = (turn == TicTacToeGame.Mark.X ? TicTacToeGame.Mark.O : TicTacToeGame.Mark.X);
            return true;
        }
    }
}
