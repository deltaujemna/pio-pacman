package pacman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void getFearTimeLeft_pacmanHasPowerupActive_fearTimeShouldBeSet() {
        // given
        maze.pacman.activatePowerup();

        for (Ghost ghost : maze.ghosts) {
            // then
            assertEquals(15, (int) ghost.getFearTimeLeft());
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
            assertEquals(LivingEntity.Direction.RIGHT, ghost.direction);
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
            assertEquals(LivingEntity.Direction.LEFT, ghost.direction);
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
            assertEquals(LivingEntity.Direction.UP, ghost.direction);
        }
    }

    @Test
    public void isBase_xLessThan180_shouldBeOutside() {
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
    public void isBase_yLessThan141_shouldBeOutside() {
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
    public void isBase_yMoreThan180_shouldBeOutside() {
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
            assertTrue(ghost.alive);
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
            assertTrue(ghost.alive);

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
            assertTrue(ghost.alive);

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
    public void teleportGhost_ghostNearTeleport_shouldMoveToRightSideOfMapAndNotChangeDirection() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 20;
            ghost.y = 180;
            ghost.direction = LivingEntity.Direction.LEFT;

            // when
            ghost.teleportGhost();

            // then
            assertEquals(380, ghost.x);
            assertEquals(180, ghost.y);
            assertEquals(LivingEntity.Direction.LEFT, ghost.direction);
        }
    }

    @Test
    public void teleportGhost_ghostNearTeleport_shouldMoveToLeftSideOfMapAndNotChangeDirection() {
        for (Ghost ghost : maze.ghosts) {
            // given
            ghost.x = 380;
            ghost.y = 180;
            ghost.direction = LivingEntity.Direction.RIGHT;

            // when
            ghost.teleportGhost();

            // then
            assertEquals(20, ghost.x);
            assertEquals(180, ghost.y);
            assertEquals(LivingEntity.Direction.RIGHT, ghost.direction);
        }
    }
}