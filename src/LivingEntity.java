import java.awt.*;

public abstract class LivingEntity extends Entity {
    boolean alive;
    double speed; // wyrażona w pikselach na klatkę

    enum Direction {UP, DOWN, LEFT, RIGHT}

    Direction direction;
    Direction directionFuture;
    Pacman f;


    boolean[][] board;

    // przekazanie położenie ścian do tej klasy
    public void pushBoard(boolean[][] board) {
        this.board = board;
    }

    // zwraca, czy postać może się poruszyć
    
    public boolean canMove(){
        if(canChangeDirection(directionFuture)){
            return true;
        }else return canChangeDirection(direction);
    }

    public boolean canChangeDirection(Direction directionFuture) {
        if (directionFuture != null  ){

                final boolean BOARD_WALL = false;

                if (board == null) {
                    throw new NullPointerException();
                }

                Rectangle boundsNext = getBounds();

                // obliczamy położenie Entity w następnej klatce
                switch (directionFuture) {
                    case UP:
                        boundsNext.y -= speed;
                    case DOWN:
                        boundsNext.y += speed;
                    case LEFT:
                        boundsNext.x -= speed;
                    case RIGHT:
                        boundsNext.x += speed;
                }
                // jeśli któryś z kątów Entity w następnej klatce znalazłby się w ścianie, to Entity nie może się poruszyć
                try {

                    if (directionFuture == Direction.LEFT || directionFuture == Direction.UP) {
                        if (board[toCellsY(boundsNext.y)][toCellsX(boundsNext.x)] == BOARD_WALL)
                            return false;
                    }
                    if (directionFuture == Direction.LEFT || directionFuture == Direction.DOWN) {
                        if (board[toCellsY(boundsNext.y + boundsNext.height - 1)][toCellsX(boundsNext.x )] == BOARD_WALL)
                            return false;
                    }
                    if (directionFuture == Direction.RIGHT || directionFuture == Direction.UP) {
                        if (board[toCellsY(boundsNext.y)][toCellsX(boundsNext.x + boundsNext.width - 1)] == BOARD_WALL)
                            return false;
                    }
                    if (directionFuture == Direction.RIGHT || directionFuture == Direction.DOWN) {
                        if (board[toCellsY(boundsNext.y + boundsNext.height - 1)][toCellsX(boundsNext.x + boundsNext.width - 1)] == BOARD_WALL)
                            return false;
                    }
                    System.out.println(""+directionFuture);
                    direction = directionFuture;
                    return true;

                } catch (ArrayIndexOutOfBoundsException e) {
                    return false;
                }

        } else{
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