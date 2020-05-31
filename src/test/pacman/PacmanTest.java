package pacman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PacmanTest {
    MazeFrame mazeFrame;
    Maze maze;

    @BeforeEach
    void setUp() {
        mazeFrame = new MazeFrame("Test", false);
        maze = mazeFrame.maze;
        maze.pauseLeft = 0;
        maze.pacman.automaticTest = true;
    }

    @Test
    public void loseLife_pacmanDoesNotDie_pacmanAndGhostsShouldResetPositions() {
        maze.pacman.x = maze.pacman.toPixels(0);
        maze.pacman.y = maze.pacman.toPixels(0);
        maze.ghosts[0].x = maze.pacman.x;
        maze.ghosts[0].y = maze.pacman.y;

        try {
            Thread.sleep(100); //czekamy na update(), tu następuje wywołanie loseLife()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        maze.pauseLeft = 500; //aby Pacman i duchy na pewno się nie ruszały

        assertEquals(maze.pacman.startX, maze.pacman.x);
        assertEquals(maze.pacman.startY, maze.pacman.y);
        assertEquals(LivingEntity.Direction.RIGHT, maze.pacman.direction);
        assertEquals(LivingEntity.Direction.RIGHT, maze.pacman.directionFuture);
        for (int i = 0; i < maze.ghosts.length; i++) {
            assertEquals(maze.ghosts[i].startX, maze.ghosts[i].x);
            assertEquals(maze.ghosts[i].startY, maze.ghosts[i].y);
        }
    }

    @Test
    public void loseLife_pacmanDies_gameShouldEnd() {
        for (int i = 0; i < 3; i++) { //Pacman traci wszystkie 3 życia
            maze.pauseLeft = 0;
            maze.pacman.x = maze.pacman.toPixels(0);
            maze.pacman.y = maze.pacman.toPixels(0);
            maze.ghosts[0].x = maze.pacman.x;
            maze.ghosts[0].y = maze.pacman.y;
            try {
                Thread.sleep(100); //czekamy na update(), tu następuje wywołanie loseLife()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertFalse(maze.pacman.alive);
        assertFalse(mazeFrame.running);
    }
}