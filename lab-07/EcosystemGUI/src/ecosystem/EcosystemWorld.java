package ecosystem;

import java.io.IOException;
import java.nio.file.Path;

public class EcosystemWorld {
    private final int rows;
    private final int cols;

    private int turn;

    private char[][] grid;        // current
    private char[][] initialGrid; // snapshot loaded from file

    public EcosystemWorld(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.turn = 0;
        this.grid = emptyGrid(rows, cols);
        this.initialGrid = emptyGrid(rows, cols);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getTurn() { return turn; }

    public void loadInitialFromFile(Path file) throws IOException {
        initialGrid = InitialStateIO.loadGrid(file, rows, cols);
        grid = deepCopy(initialGrid);
        turn = 0;
    }

    public void saveInitialToFile(Path file) throws IOException {
        InitialStateIO.saveGrid(file, initialGrid);
    }

    // challenge later: save current state
    public void saveCurrentToFile(Path file) throws IOException {
        InitialStateIO.saveGrid(file, grid);
    }

    public void resetToInitial() {
        grid = deepCopy(initialGrid);
        turn = 0;
    }

    // later: apply your ecosystem rules
    public void takeTurn() {
        turn++;
    }

    public char getCellSymbol(int r, int c) {
        return grid[r][c];
    }

    public String getCellSummary(int r, int c) {
        return "Cell (" + r + "," + c + ")\n"
             + "Turn: " + turn + "\n"
             + "Symbol: " + grid[r][c] + "\n"
             + "[TODO] creatures + attributes";
    }

    private static char[][] emptyGrid(int rows, int cols) {
        char[][] g = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) g[r][c] = '.';
        }
        return g;
    }

    private static char[][] deepCopy(char[][] src) {
        char[][] copy = new char[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i].clone();
        }
        return copy;
    }
}
