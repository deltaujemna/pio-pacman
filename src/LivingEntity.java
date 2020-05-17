import java.awt.*;

public abstract class LivingEntity extends Entity {
    boolean alive;
    double speed; // wyrażona w pikselach na klatkę
    int startX, startY;

    enum Direction {UP, DOWN, LEFT, RIGHT}

    Direction direction;
    Direction directionFuture;
    boolean[][] grid;

    public void setSpeed(Direction direction) {
        if (direction != null) {
            switch (direction) {
                case UP:
                    this.y -= speed;
                    break;
                case DOWN:
                    this.y += speed;
                    break;
                case LEFT:
                    this.x -= speed;
                    break;
                case RIGHT:
                    this.x += speed;
                    break;
            }
        }
    }

    // zwraca, czy postać może się poruszyć
    public boolean canMoveThisDirection(Direction direction) {
        if (direction != null) {
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
                    if (!grid[toCells(boundsNext.y)][toCells(boundsNext.x)])
                        return false;
                }
                if (direction == Direction.LEFT || direction == Direction.DOWN) {
                    if (!grid[toCells(boundsNext.y + boundsNext.height - 1)][toCells(boundsNext.x)])
                        return false;
                }
                if (direction == Direction.RIGHT || direction == Direction.UP) {
                    if (!grid[toCells(boundsNext.y)][toCells(boundsNext.x + boundsNext.width - 1)])
                        return false;
                }
                if (direction == Direction.RIGHT || direction == Direction.DOWN) {
                    if (!grid[toCells(boundsNext.y + boundsNext.height - 1)][toCells(boundsNext.x + boundsNext.width - 1)])
                        return false;
                }
                //System.out.println("speed = " + this.speed+ "direction  "+direction + " "+this ); // ukazuje ze nie dziala duchy

                // wszystkie kąty zostały sprawdzone, kolizji nie będzie
                return true;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return false;
    }

    public boolean canMoveDirectionFutureAndDirection() {
        if (canMoveThisDirection(this.directionFuture)) {
            this.direction = directionFuture;
            return true;
        } else {
            return canMoveThisDirection(this.direction);
        }
    }

    // teleportuje postać na wskazane x, y
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean teleport() {
        if (this.y == 180) {
            if (this.x == 20) {
                teleport(toPixels(18), toPixels(8));
                this.x--;
                return true;
            } else if (toCells(this.x) == 18) {
                teleport(toPixels(0), toPixels(8));
                this.x++;
                return true;
            }
        }
        return false;
    }
}