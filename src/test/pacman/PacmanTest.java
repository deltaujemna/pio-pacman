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
    public void activatePowerup(){
        maze.pacman.activatePowerup();
        assertEquals(maze.pacman.getPowerUpTimeLeft(), maze.pacman.getPOWERUP_TIME());

        for (Ghost ghost : maze.ghosts) {
            assertTrue(ghost.isFrightened());
            assertEquals(ghost.getFearTimeLeft(), 15);
        }

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
        //Pacman nie rusza się z miejsca, więc zjadł tylko 1 kulkę, zatem >100 pkt musi być
        //za zjedzenie ducha
        assertTrue(finalScore > 100);
        assertFalse(maze.ghosts[0].alive);
    }

    @Test
    public void teleportGhosts(){
        for( Ghost ghost : maze.ghosts){
            ghost.y = 180;
            ghost.x = 20;
            ghost.direction = LivingEntity.Direction.LEFT;
            ghost.teleportGhost();
        }

        for( Ghost ghost : maze.ghosts){
            assertEquals(ghost.x, 380);
            assertEquals(ghost.y, 180);
            assertEquals(ghost.direction, LivingEntity.Direction.LEFT);
        }

        for( Ghost ghost : maze.ghosts){
            ghost.y = 180;
            ghost.x = 380;
            ghost.direction = LivingEntity.Direction.RIGHT;
            ghost.teleportGhost();
        }

        for( Ghost ghost : maze.ghosts){
            assertEquals(ghost.x, 20);
            assertEquals(ghost.y, 180);
            assertEquals(ghost.direction, LivingEntity.Direction.RIGHT);
        }
    }

}