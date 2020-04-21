import java.awt.Rectangle;

public abstract class LivingEntity implements Entity {

    private boolean alive;
    private double speed; // wyrażona w pikselach na klatkę
    private enum Direction {UP, DOWN, LEFT, RIGHT}
    private Direction direction;
    private boolean[][] board;


    public abstract void tick();

    public void pushBoard(boolean[][] board) {
        this.board = board;
    }

    public boolean canMove() {
        final boolean BOARD_WALL = true;

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
        if(board[boundsNext.y / 20][boundsNext.x / 20] == BOARD_WALL)
            return false;
        if(board[(boundsNext.y + boundsNext.height) / 20][boundsNext.x / 20] == BOARD_WALL)
            return false;
        if(board[boundsNext.y / 20][(boundsNext.x + boundsNext.width) / 20] == BOARD_WALL)
            return false;
        if(board[(boundsNext.y + boundsNext.height) / 20][(boundsNext.x + boundsNext.width) / 20] == BOARD_WALL)
            return false;

        // wszystkie kąty zostały sprawdzone, kolizji nie będzie
        return true;
    }

    @Override
    public void render() {
        // TODO: zrobić
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
