import java.awt.*;

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

        if (board == null)
            throw new NullPointerException();

        Rectangle boundsNext = getBounds();

        // obliczamy położenie Entity w następnej klatce
        switch (direction) {
            case UP:
                boundsNext.y -= speed;
                break;
            case DOWN:
                boundsNext.y += speed;
                break;
            case LEFT:
                boundsNext.x -= speed;
                break;
            case RIGHT:
                boundsNext.x += speed;
        }

        // jeśli któryś z kątów Entity w następnej klatce znalazłby się w ścianie, to Entity nie może się poruszyć
        try {
            if(direction == Direction.LEFT || direction == Direction.UP) {
                if (board[toCellsY(boundsNext.y)][toCellsX(boundsNext.x)] == BOARD_WALL)
                    return false;
            }
            if(direction == Direction.LEFT || direction == Direction.DOWN) {
                if (board[toCellsY(boundsNext.y + boundsNext.height - 1)][toCellsX(boundsNext.x)] == BOARD_WALL)
                    return false;
            }
            if(direction == Direction.RIGHT || direction == Direction.UP) {
                if (board[toCellsY(boundsNext.y)][toCellsX(boundsNext.x + boundsNext.width - 1)] == BOARD_WALL)
                    return false;
            }
            if(direction == Direction.RIGHT || direction == Direction.DOWN) {
                if (board[toCellsY(boundsNext.y + boundsNext.height - 1)][toCellsX(boundsNext.x + boundsNext.width - 1)] == BOARD_WALL)
                    return false;
            }

            // wszystkie kąty zostały sprawdzone, kolizji nie będzie
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // teleportuje postać na wskazane x, y
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Graphics g) {
        // TODO: zrobić
    }
}