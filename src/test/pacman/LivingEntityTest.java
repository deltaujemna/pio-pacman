package pacman;

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
    void setSpeed_null() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;

        maze.pacman.setSpeed(null);

        assertEquals(x, maze.pacman.x);
        assertEquals(y, maze.pacman.y);
    }

    @Test
    void setSpeed_direction_right() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;

        maze.pacman.setSpeed(LivingEntity.Direction.RIGHT);

        assertEquals(x + maze.pacman.speed, maze.pacman.x);
        assertEquals(y, maze.pacman.y);
    }

    @Test
    void setSpeed_direction_left() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;

        maze.pacman.setSpeed(LivingEntity.Direction.LEFT);

        assertEquals(x - maze.pacman.speed, maze.pacman.x);
        assertEquals(y, maze.pacman.y);
    }

    @Test
    void setSpeed_direction_down() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;

        maze.pacman.setSpeed(LivingEntity.Direction.DOWN);

        assertEquals(x, maze.pacman.x);
        assertEquals(y + maze.pacman.speed, maze.pacman.y);
    }

    @Test
    void setSpeed_direction_Up() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;

        maze.pacman.setSpeed(LivingEntity.Direction.UP);

        assertEquals(x, maze.pacman.x);
        assertEquals(y - maze.pacman.speed, maze.pacman.y);
    }

    @Test
    void canMoveThisDirection_test_null_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = null;

        update_once();

        assertEquals(x, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }

    @Test
    void canMoveThisDirection_test_Right_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = LivingEntity.Direction.RIGHT;

        update_once();

        assertEquals(x + maze.pacman.speed, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }

    @Test
    void canMoveThisDirection_test_Left_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = LivingEntity.Direction.LEFT;

        update_once();

        assertEquals(x - maze.pacman.speed, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }


    @Test
    void canMoveThisDirection_test_Up_direction() {
        int x = maze.pacman.x;
        int y = maze.pacman.y;
        maze.pacman.direction = LivingEntity.Direction.UP;

        update_once();

        assertEquals(x, maze.pacman.x);
        assertEquals(y, maze.pacman.y);

    }

    @Test
    void canMoveThisDirection_test_Down_direction() {
        int length = maze.pacman.toPixels(maze.grid.length );
        for(int x =21;x < length;x += 10) {
            for(int y = 21;y < length ;y+=10) {

                int pacmanSpeed = (int) maze.pacman.speed;


                if (maze.grid[maze.pacman.toCells(y-pacmanSpeed)][maze.pacman.toCells(x)]) {
                    assertFalse(maze.pacman.canMoveThisDirection(LivingEntity.Direction.DOWN));
                }

            }
        }

    }

    private void update_once() {
        try {
            Thread.sleep(1500 / 60); //czekamy na update(), tu następuje wywołanie canMoveThisDirection_test
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}