package pacman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MazeTest {
    MazeFrame mazeFrame;
    Maze maze;

    @BeforeEach
    void setUp() {
        mazeFrame = new MazeFrame("Test", false);
        maze = mazeFrame.maze;
    }

    @Test
    public void updateMap_update8Cells_gridTableShouldContainProperValues() {
        int x = 40;
        int y = 80;
        int width = 80;
        int height = 40;

        //Najpierw ustawiamy wszystkie elementy na true
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 19; j++)
                maze.grid[i][j] = true;

        maze.updateMap(x, y, width, height);

        for (int i = 2; i < 5; i++)
            for (int j = 4; j < 5; j++)
                assertFalse(maze.grid[j][i]);
    }

    @Test
    public void updateMap_eneteredValuesDoNotCoverWholeCells_cellsShouldNotBeUpdated() {
        //Najpierw ustawiamy wszystkie elementy na true
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 19; j++)
                maze.grid[i][j] = true;

        maze.updateMap(1, 1, 10, 10);
        maze.updateMap(33, 33, 5, 5);
        maze.updateMap(100, 100, 19, 19);
        maze.updateMap(99, 99, 1, 1);
        maze.updateMap(130, 130, 15, 15);

        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 19; j++)
                assertTrue(maze.grid[j][i]);
    }
}