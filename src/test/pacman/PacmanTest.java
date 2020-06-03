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
        //oczekujemy, że pacman ma <100 pkt (bo nie powinien zjeść ducha, ale mógł zjeść jakieś kulki)
        assertTrue(maze.pacman.getScore() < 100);
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

        try {
            Thread.sleep(100); //czekamy na update(), tu następuje wywołanie collision()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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