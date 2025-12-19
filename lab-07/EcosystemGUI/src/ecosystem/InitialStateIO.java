package ecosystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InitialStateIO {

    public static char[][] loadGrid(Path file, int rows, int cols) throws IOException {
        List<String> lines = Files.readAllLines(file);
        if (lines.size() < rows) {
            throw new IOException("Expected at least " + rows + " lines, got " + lines.size());
        }

        char[][] grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);
            if (line.length() < cols) {
                throw new IOException("Line " + (r + 1) + " is too short. Expected " + cols + " chars.");
            }
            for (int c = 0; c < cols; c++) {
                grid[r][c] = line.charAt(c);
            }
        }

        return grid;
    }

    public static void saveGrid(Path file, char[][] grid) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                sb.append(grid[r][c]);
            }
            sb.append(System.lineSeparator());
        }
        Files.writeString(file, sb.toString());
    }
}
