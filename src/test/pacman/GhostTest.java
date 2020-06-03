package pacman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GhostTest {
    MazeFrame mazeFrame;
    Maze maze;

    @BeforeEach
    void setUp() {
        mazeFrame = new MazeFrame("Test", false);
        maze = mazeFrame.maze;
        maze.pauseLeft = 0;
        maze.pacman.automaticTest = true;
    }

    // testy metody isBase()
    @Test
    public void isBase_directionShouldBeRIGHT() {
        for (Ghost ghost : maze.ghosts) {
            ghost.x = 180;
            assertEquals(ghost.direction, LivingEntity.Direction.RIGHT);
        }
    }

    @Test
    public void isBase_directionShouldBeLEFT() {
        for (Ghost ghost : maze.ghosts) {
            ghost.x = 220;
            assertEquals(ghost.direction, LivingEntity.Direction.RIGHT);
        }
    }

    @Test
    public void isBase_directionShouldBeUP() {
        for (Ghost ghost : maze.ghosts) {
            ghost.x = 220;
            assertEquals(ghost.direction, LivingEntity.Direction.RIGHT);
        }
    }

    @Test
    public void isBase_outside() {
        for (Ghost ghost : maze.ghosts) {
            LivingEntity.Direction dir = ghost.direction;
            ghost.x = 199;
            assertEquals(dir, ghost.direction);
        }
    }

    // testy metody render
    @Test
    public void render_Frightened() {
        for (Ghost ghost : maze.ghosts) {
            ghost.alive = true;
            ghost.setFearTimeLeft();
            ghost.render(mazeFrame.getGraphics());
            assertEquals("Images/ghost_frightened.png", ghost.imgPath);
        }
    }

    @Test
    public void render_notFrightened() {
        for (Ghost ghost : maze.ghosts) {
            ghost.alive = true;
            ghost.render(mazeFrame.getGraphics());
            assertNotEquals("Images/ghost_frightened.png", ghost.imgPath);
        }
    }

    @Test
    public void render_alive() {
        for (Ghost ghost : maze.ghosts) {
            ghost.alive = true;
            ghost.render(mazeFrame.getGraphics());
            assertFalse(ghost.imgPath.startsWith("Images/ghost_dead"));
        }
    }

    @Test
    public void render_dead() {
        for (Ghost ghost : maze.ghosts) {
            ghost.alive = false;
            ghost.render(mazeFrame.getGraphics());
            assertTrue(ghost.imgPath.startsWith("Images/ghost_dead"));
        }
    }

}