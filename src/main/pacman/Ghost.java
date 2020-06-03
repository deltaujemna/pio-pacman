package pacman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Ghost extends LivingEntity {

    private double fearTimeLeft;
    private double deadTimeLeft;

    public int points = 200;     //tymczasowo
    int ghostNumber;//jeżeli każdy duch ma inny kolor

    long timeDecideDirection;

    int pacmanX; //aktualna pozycja Pacmana, żeby można było go śledzić
    int pacmanY;

    Direction pacmanDirection;
    Direction pacmanDirectionFuture;

    public String imgPath;

    private final TrackPacman trackPacman;

    public Ghost(int x, int y, int ghostNumber) {
        this.ghostNumber = ghostNumber;
        this.startX = toPixels(x);
        this.startY = toPixels(y);
        this.x = toPixels(x);
        this.y = toPixels(y);
        int size = 20;
        this.width = size;
        this.height = size;
        this.speed = 1;
        this.direction = Direction.RIGHT;
        this.directionFuture = Direction.UP;
        this.timeDecideDirection = System.nanoTime();
        this.alive = true;
        trackPacman = new TrackPacman(this);
    }

    public void pushPacmanX(int x) {
        pacmanX = x;
    }

    public void pushPacmanY(int y) {
        pacmanY = y;
    }

    public void pushPacmanDirection(Direction direction) {
        pacmanDirection = direction;
    }

    public void pushPacmanDirectionFuture(Direction direction) {
        pacmanDirectionFuture = direction;
    }

    protected boolean isBase() {
        if (this.y <= 180 && this.y > 140) {
            if ((180 <= this.x) && (200 > this.x)) {
                direction = Direction.RIGHT;
                return true;
            } else if ((this.x <= 220) && (200 < this.x)) {
                direction = Direction.LEFT;
                return true;
            } else if (this.x == 200) {
                direction = Direction.UP;
                return true;
            }
        }
        return false;
    }


    public boolean isFrightened() {
        return this.fearTimeLeft > 0;
    }

    public void die() {
        this.alive = false;
        this.deadTimeLeft = 10;
        direction = null;
    }

    public void setFearTimeLeft() {
        this.fearTimeLeft = 15; //jeżeli 15 sekund trwa power-up
    }

    public double getFearTimeLeft() {
        return this.fearTimeLeft;
    }

    public void tick() {
        if (alive) {
            this.speed = 1;
            if (!isBase()) {
                if (!teleportGhost()) {
                    if (fearTimeLeft > 0) {
                        trackPacman.escapeFromPacman();
                    } else {
                        trackPacman.trackPacman();
                    }
                }


            }
        }

        setSpeed(direction);

        if (fearTimeLeft > 0) {
            fearTimeLeft -= (double) 1 / 60;
        }

        if (deadTimeLeft > 0) {
            trackPacman.goToCage();
            deadTimeLeft -= (double) 1 / 60;
            if (deadTimeLeft <= 0)
                alive = true;
        }

    }

    public boolean teleportGhost() {
        if (this.y == 180) {
            if (this.x < 80) {
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                    if (this.x == 20) {
                        teleport(380, 180);
                    }
                    return true;
                }
            } else if (this.x > 300) {
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                    if (this.x == 380) {
                        teleport(20, 180);
                    }
                }
                return true;
            }
        }
        return false;
    }


    public void render(Graphics g) {
        if (alive) {
            if (isFrightened()) {
                imgPath = "Images/ghost_frightened.png";
            } else {
                imgPath = "Images/ghost" + ghostNumber + "_right" + ".png";
                if (direction == Direction.RIGHT || directionFuture == Direction.RIGHT)
                    imgPath = "Images/ghost" + ghostNumber + "_right" + ".png";
                if (direction == Direction.LEFT || directionFuture == Direction.LEFT)
                    imgPath = "Images/ghost" + ghostNumber + "_left" + ".png";
            }
        } else {
            imgPath = "Images/ghost_dead_right.png";
            if (direction == Direction.RIGHT || directionFuture == Direction.RIGHT)
                imgPath = "Images/ghost_dead_right.png";
            if (direction == Direction.LEFT || directionFuture == Direction.LEFT)
                imgPath = "Images/ghost_dead_left.png";
        }

        try {
            g.drawImage(ImageIO.read(new File(imgPath)), (int) ((this.x + Maze.deltaX) * Maze.scale), (int) ((this.y + Maze.deltaY) * Maze.scale),
                    (int) (this.width * Maze.scale), (int) (this.height * Maze.scale), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}