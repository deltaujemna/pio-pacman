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

    @Test
    void canMoveThisDirection() {
        System.out.println(maze.grid[1].length);
        for(int i = 0;i < maze.grid.length;i++){
            for(int z = 0;z < maze.grid[i].length;z++){
                System.out.println(maze.grid[i][z]);
            }
        }


    }

    @Test
    void teleport() {
    }

}