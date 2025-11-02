public class World implements TurnTaker {

    private Tile[][] grid;

    public World(int rows, int cols) {
        grid = new Tile[rows][cols];

        // Initialize each Tile with some default environment.
        // Later this will come from JSON config.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Example default environment:
                int water = 50;
                int temp = 20;
                int nutrient = 70;
                grid[r][c] = new Tile(water, temp, nutrient);
            }
        }
    }

    public Tile getTile(int r, int c) {
        return grid[r][c];
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }

    // World turn:
    // loop through every tile and advance it.
    @Override
    public void takeTurn() {
        System.out.println("=== World turn start ===");
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c].takeTurn();
            }
        }
        System.out.println("=== World turn end ===");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("World:\n");
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                sb.append("[").append(r).append(",").append(c).append("] ")
                  .append(grid[r][c].toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
