package pacman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void activatePowerup_pacmanEatsPowerDot_powerupShouldBeActivated() {
        maze.pacman.activatePowerup();
        assertEquals(maze.pacman.getPOWERUP_TIME(), maze.pacman.getPowerUpTimeLeft());
    }

    @Test
    public void collision_pacmanPowerupActive_ghostDies() {
        maze.pacman.activatePowerup();
        maze.ghosts[0].x = maze.pacman.x;
        maze.ghosts[0].y = maze.pacman.y;

        maze.pacman.collision(maze.ghosts[0]);
        int finalScore = maze.pacman.getScore();

        assertTrue(maze.pacman.alive);
        //Pacman nie rusza się z miejsca, więc zjadł tylko 1 kulkę, zatem >100 pkt musi
        //być za zjedzenie ducha
        assertTrue(finalScore > 100);
        assertFalse(maze.ghosts[0].alive);
    }

    @Test
    public void collision_pacmanPowerupNotActive_ghostIsAliveAndPacmanLosesLife() {
        int livesBefore = maze.pacman.getLives();

        maze.pacman.collision(maze.pacman.ghosts[0]);

        assertEquals(livesBefore - 1, maze.pacman.getLives());
        assertTrue(maze.ghosts[0].alive);
        assertTrue(maze.pacman.alive); // pacman wciąż ma życia
    }

    @Test
    public void collision_pacmanPowerupNotActiveAndHasOneLife_pacmanDies() {
        int livesBefore = maze.pacman.getLives();

        for (int i = 0; i < livesBefore; i++) {
            maze.pacman.collision(maze.ghosts[0]);
        }

        assertEquals(0, maze.pacman.getLives());
        assertTrue(maze.ghosts[0].alive);
        assertFalse(maze.pacman.alive);
    }
}