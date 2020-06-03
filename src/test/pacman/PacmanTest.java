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
    public void activatePowerup_pacmanHasPowerupActive_ghostsShouldBeFrightened() {
        // given
        maze.pacman.activatePowerup();

        for (Ghost ghost : maze.ghosts) {
            // then
            assertTrue(ghost.isFrightened());
        }
    }

    @Test
    public void collision_pacmanPowerupActive_ghostDies() {
        maze.pacman.activatePowerup();
        int startLives = maze.pacman.getLives();
        maze.ghosts[0].x = maze.pacman.x;
        maze.ghosts[0].y = maze.pacman.y;

        maze.pacman.collision(maze.ghosts[0]);
        int finalScore = maze.pacman.getScore();

        assertTrue(maze.pacman.alive);
        assertEquals(startLives, maze.pacman.getLives());
        //Pacman nie rusza się z miejsca, więc zjadł tylko 1 kulkę, zatem >100 pkt musi
        //być za zjedzenie ducha
        assertTrue(finalScore > 100);
        assertFalse(maze.ghosts[0].alive);
    }

    @Test
    public void collision_pacmanLosesLifeAndIsStillAlive_pacmanAndGhostsShouldResetPositions() {
        int startLives = maze.pacman.getLives();
        maze.pauseLeft = 500;
        maze.ghosts[0].x = maze.pacman.x;
        maze.ghosts[0].y = maze.pacman.y;

        maze.pacman.collision(maze.ghosts[0]);

        assertEquals(startLives - 1, maze.pacman.getLives());
        assertTrue(maze.ghosts[0].alive);
        assertEquals(maze.pacman.toCells(maze.pacman.startX), maze.pacman.toCells(maze.pacman.x));
        assertEquals(maze.pacman.toCells(maze.pacman.startY), maze.pacman.toCells(maze.pacman.y));
        assertEquals(maze.ghosts[0].toCells(maze.pacman.startY), maze.ghosts[0].toCells(maze.pacman.y));
        assertEquals(maze.ghosts[0].toCells(maze.pacman.startY), maze.ghosts[0].toCells(maze.pacman.y));
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

    @Test
    public void tick_pacmanHasPowerup_DecreasePowerupTimeLeft() {
        maze.pacman.activatePowerup();
        double timeLeftBefore = maze.pacman.getPowerUpTimeLeft();

        maze.pacman.tick();

        assertTrue(maze.pacman.getPowerUpTimeLeft() < timeLeftBefore);
    }

    @Test
    public void tick_pacmanIntersectsWithDot_increaseScore() {
        // w momencie rozpoczęcia na każdym polu znajduje się punkt
        // w celu zebrania dokładnie jednego punktu kierujemy pacmana w ścianę, aby nie mógł się poruszyć
        maze.pacman.direction = LivingEntity.Direction.DOWN;

        maze.pacman.tick();

        Dot testDot = new Dot(-10, -10); // w celu pobrania punktów za zebranie tworzymy punkt poza planszą
        assertEquals(testDot.points, maze.pacman.getScore());
    }

}