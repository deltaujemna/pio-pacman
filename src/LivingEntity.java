import java.awt.Rectangle;
import java.awt.Graphics;

public abstract class LivingEntity extends Entity {
    boolean alive;
    double speed; // wyrażona w pikselach na klatkę
    enum Direction {UP, DOWN, LEFT, RIGHT}
    Direction direction;

    boolean[][] board;

    // przekazanie położenie ścian do tej klasy
    public void pushBoard(boolean[][] board) {
        this.board = board;
    }

    // zwraca, czy postać może się poruszyć
    public boolean canMove() {
        final boolean BOARD_WALL = false;

        if(board == null)
            throw new NullPointerException();

        Rectangle boundsNext = getBounds();

        // obliczamy położenie Entity w następnej klatce
        switch(direction) {
            case UP:
                boundsNext.y -= speed;
            case DOWN:
                boundsNext.y += speed;
            case LEFT:
                boundsNext.x -= speed;
            case RIGHT:
                boundsNext.x += speed;
        }

        // jeśli któryś z kątów Entity w następnej klatce
        // znalazłby się w ścianie, to Entity nie może
        // się poruszyć
        try {
            if (board[toCells(boundsNext.y - BOARD_START_Y)][toCells(boundsNext.x - BOARD_START_X)] == BOARD_WALL)
                return false;
            if (board[toCells(boundsNext.y + boundsNext.height - 1 - BOARD_START_Y)][toCells(boundsNext.x - BOARD_START_X)] == BOARD_WALL)
                return false;
            if (board[toCells(boundsNext.y - BOARD_START_Y)][toCells(boundsNext.x + boundsNext.width - 1 - BOARD_START_X)] == BOARD_WALL)
                return false;
            if (board[toCells(boundsNext.y + boundsNext.height - 1 - BOARD_START_Y)][toCells(boundsNext.x + boundsNext.width - 1 - BOARD_START_X)] == BOARD_WALL)
                return false;

            // wszystkie kąty zostały sprawdzone, kolizji nie będzie
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // teleportuje postać na wskazane x/y
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Graphics g) {
        // TODO: zrobić
    }
}