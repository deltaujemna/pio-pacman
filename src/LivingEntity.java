import java.awt.*;

public abstract class LivingEntity extends Entity {
    boolean alive;
    double speed; // wyrażona w pikselach na klatkę

    enum Direction {UP, DOWN, LEFT, RIGHT}
    enum LivingObject{PACMAN,GHOST}
    static Direction directionPacman;
    static Direction direction;
    Direction directionFuture;
    boolean[][] grid;

    // zwraca, czy postać może się poruszyć
    private boolean canMoveThisDirection(Direction direction) {
        if(direction != null) {

            final boolean GRID_WALL = false;

            if (grid == null)
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
                if (direction == Direction.LEFT || direction == Direction.UP) {
                    if (grid[toCellsY(boundsNext.y)][toCellsX(boundsNext.x)] == GRID_WALL)
                        return false;
                }
                if (direction == Direction.LEFT || direction == Direction.DOWN) {
                    if (grid[toCellsY(boundsNext.y + boundsNext.height - 1)][toCellsX(boundsNext.x)] == GRID_WALL)
                        return false;
                }
                if (direction == Direction.RIGHT || direction == Direction.UP) {
                    if (grid[toCellsY(boundsNext.y)][toCellsX(boundsNext.x + boundsNext.width - 1)] == GRID_WALL)
                        return false;
                }
                if (direction == Direction.RIGHT || direction == Direction.DOWN) {
                    if (grid[toCellsY(boundsNext.y + boundsNext.height - 1)][toCellsX(boundsNext.x + boundsNext.width - 1)] == GRID_WALL)
                        return false;
                }
                //System.out.println("speed = " + this.speed+ "direction  "+direction + " "+this ); // ukazuje ze nie dziala duchy

                // wszystkie kąty zostały sprawdzone, kolizji nie będzie

                this.direction = direction;
                return true;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return false;
    }
    public boolean canMove(){
        if(canMoveThisDirection(this.directionFuture)){
            return true;
        }else if(canMoveThisDirection(this.direction)) {
            return true;
        }
        return false;

    }
    // teleportuje postać na wskazane x, y
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean teleport(){
        if(this.y == 180){
            if(this.x == 20){
                teleport(toPixelsX(18),toPixelsY(8));
                this.x--;
                return true;
            }else if(toCellsX(this.x) == 18){
                teleport(toPixelsX(0),toPixelsY(8));
                this.x++;
                return true;
            }
        }
        return false;

    }


    @Override
    public void render(Graphics g) {
        // TODO: zrobić
    }
}