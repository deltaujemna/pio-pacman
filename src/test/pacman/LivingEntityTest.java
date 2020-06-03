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
        maze.pauseLeft = 1000;
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
    void canMoveThisDirection_test_Right_direction() {
        int speed = (int) maze.pacman.speed;
        canMoveThisDirection_test_this_direction(LivingEntity.Direction.UP,speed,0);

    }

    @Test
    void canMoveThisDirection_test_Left_direction() {
        int speed = -(int) maze.pacman.speed;
        canMoveThisDirection_test_this_direction(LivingEntity.Direction.UP,speed,0);

    }


    @Test
    void canMoveThisDirection_test_Up_direction() {
        int speed = -(int) maze.pacman.speed;
        canMoveThisDirection_test_this_direction(LivingEntity.Direction.UP,0,speed);

    }

    @Test
    void canMoveThisDirection_test_Down_direction() {
        int speed = (int) maze.pacman.speed;
        canMoveThisDirection_test_this_direction(LivingEntity.Direction.DOWN,0,speed);

    }
    private void canMoveThisDirection_test_this_direction(LivingEntity.Direction direction,int speedX,int speedY) {
        int length = maze.pacman.toPixels(maze.grid.length );
        for(int x =21;x < length;x += 10) {
            for(int y = 21;y < length ;y+=10) {

                if (maze.grid[maze.pacman.toCells(y +  speedY)][maze.pacman.toCells(x + speedX)]) {
                    assertFalse(maze.pacman.canMoveThisDirection(direction));
                }

            }
        }

    }

    @Test
    void canMoveThisDirection_test_null_direction() {
        canMoveThisDirection_test_this_direction(null,0,0);

    }

}