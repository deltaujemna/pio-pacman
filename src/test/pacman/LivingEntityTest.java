package pacman;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivingEntityTest {
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
    void setSpeed_null_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = null;

        update_once();

        assertEquals(x, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }

    @Test
    void setSpeed_Right_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = LivingEntity.Direction.RIGHT;

        update_once();

        assertEquals(x + maze.pacman.speed, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }

    @Test
    void setSpeed_Left_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = LivingEntity.Direction.LEFT;

        update_once();

        assertEquals(x - maze.pacman.speed, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }


    @Test
    void setSpeed_Up_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = LivingEntity.Direction.UP;

        update_once();

        assertEquals(x, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }

    @Test
    void setSpeed_Down_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = LivingEntity.Direction.DOWN;

        update_once();

        assertEquals(x, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }


    private void update_once() {
        try {
            Thread.sleep(1000 / 60); //czekamy na update(), tu następuje wywołanie setSpeed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}