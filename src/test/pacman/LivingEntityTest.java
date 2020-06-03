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

    @AfterEach
    void tearDown() {
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


    private void update_once(){
        try {
            Thread.sleep(1010/60); //czekamy na update(), tu następuje wywołanie setSpeed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void canMoveThisDirection() {
//        System.out.println(maze.getWidth());
//        System.out.println(maze.getHeight());
//        for(int x = 21;x < (maze.pacman.toPixels(maze.grid.length) - 1) ;x++) {
//            for (int y = 21; y < (maze.pacman.toPixels(maze.grid.length) - 1); y++) {
//                maze.pacman.x = x;
//                maze.pacman.y = y;
//                maze.pacman.direction = null;
//
//                boolean tmp = maze.grid[maze.pacman.toCells(x)][maze.pacman.toCells(y)];
//
//                update_once();
//
//                if (!tmp) {
//                    assertEquals(x,maze.pacman.x);
//                    assertEquals(y,maze.pacman.y);
//                }
//            }
//
//
//        }
//    }
//    @Test
//    void canMoveThisDirection_1() {
//        int x = 100;
//        int y = 200;
//        maze.pacman.x = x;
//        maze.pacman.y = y;
//        maze.pacman.direction = LivingEntity.Direction.RIGHT;
//
//        boolean tmp = maze.grid[maze.pacman.toCells((int) (x + maze.pacman.speed))][maze.pacman.toCells(y)];
//
//        update_once();
//
//        if (tmp) {
//            assertEquals(x, maze.pacman.x);
//            assertEquals(y, maze.pacman.y);
//        }else {
//            assertEquals(x +maze.pacman.speed,maze.pacman.x);
//        }
//    }

    @Test
    void teleport() {


    }

}