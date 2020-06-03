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

    @Test
    public void isFrightened_activatePowerup_GhostsShouldBeFrightened() {
        // given
        maze.pacman.activatePowerup();

        for (Ghost ghost : maze.ghosts) {
            // then
            assertTrue(ghost.isFrightened());
        }
    }

    @Test
    public void getFearTimeLeft_activatePowerup_FearTimeShouldBe15() {
        // given
        maze.pacman.activatePowerup();

        for (Ghost ghost : maze.ghosts) {
            // then
            assertEquals((int)ghost.getFearTimeLeft(), 15);
        }
    }

    @Test
    public void isBase_xBetween180and199_directionShouldBeRIGHT() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 180;

            // when
            ghost.tick();

            // then
            assertEquals(ghost.direction, LivingEntity.Direction.RIGHT);
        }
    }

    @Test
    public void isBase_isBase_xBetween201and220_directionShouldBeLEFT() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 220;

            // when
            ghost.tick();

            // then
            assertEquals(ghost.direction, LivingEntity.Direction.LEFT);
        }
    }

    @Test
    public void isBase_xEqualsTo200_directionShouldBeUP() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 200;

            // when
            ghost.tick();

            // then
            assertEquals(ghost.direction, LivingEntity.Direction.UP);
        }
    }

    @Test
    public void isBase_xLessThan180_shouldeBeOutside() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 179;

            // when
            boolean isInBase = ghost.isBase();

            // then
            assertFalse(isInBase);
        }
    }

    @Test
    public void isBase_yLessThan141_shouldeBeOutside() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.y = 140;

            // when
            boolean isInBase = ghost.isBase();

            // then
            assertFalse(isInBase);
        }
    }

    @Test
    public void isBase_yMoreThan180_shouldeBeOutside() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.y = 181;

            // when
            boolean isInBase = ghost.isBase();

            // then
            assertFalse(isInBase);
        }
    }

    @Test
    public void render_ghostsAreAliveAndFrightened_imgPathShouldLeadToFrightenedGhostsImages() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.alive = true;
            ghost.setFearTimeLeft();

            // when
            ghost.render(mazeFrame.getGraphics());

            // then
            assertEquals("Images/ghost_frightened.png", ghost.imgPath);
        }
    }

    @Test
    public void render_ghostsAreAliveAndNotFrightened_imgPathShouldNotLeadToFrightenedGhostsImages() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.alive = true;

            // when
            ghost.render(mazeFrame.getGraphics());

            // then
            assertNotEquals("Images/ghost_frightened.png", ghost.imgPath);
        }
    }

    @Test
    public void render_ghostsAreAlive_imgPathShouldNotLeadToDeadGhostsImages() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.alive = true;

            // when
            ghost.render(mazeFrame.getGraphics());

            // then
            assertFalse(ghost.imgPath.startsWith("Images/ghost_dead"));
        }
    }

    @Test
    public void render_ghostsAreNotAlive_imgPathShouldLeadToDeadGhostsImages() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.alive = false;

            // when
            ghost.render(mazeFrame.getGraphics());

            // then
            assertTrue(ghost.imgPath.startsWith("Images/ghost_dead"));
        }
    }

    @Test
    public void teleportGhost_WhenNearTeleport_SameY_XLargerBy360_SameDirection() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 20;
            ghost.y = 180;
            ghost.direction = LivingEntity.Direction.LEFT;

            // when
            ghost.teleportGhost();
        }

        for (Ghost ghost : maze.ghosts) {
            // then
            assertEquals(ghost.x, 380);
            assertEquals(ghost.y, 180);
            assertEquals(ghost.direction, LivingEntity.Direction.LEFT);
        }
    }

    @Test
    public void teleportGhost_WhenNearTeleport_SameY_XSmallerBy360_SameDirection() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 380;
            ghost.y = 180;
            ghost.direction = LivingEntity.Direction.RIGHT;

            // when
            ghost.teleportGhost();
        }

        for (Ghost ghost : maze.ghosts) {
            // then
            assertEquals(ghost.x, 20);
            assertEquals(ghost.y, 180);
            assertEquals(ghost.direction, LivingEntity.Direction.RIGHT);
        }
    }
}