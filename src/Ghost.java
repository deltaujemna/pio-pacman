import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Ghost extends LivingEntity {

    double fearTimeLeft;
    double deadTimeLeft;

    int points = 200;     //tymczasowo
    int ghostNumber;//jeżeli każdy duch ma inny kolor

    final int size = 20;
    long timeDecideDirection;

    int pacmanX; //aktualna pozycja Pacmana, żeby można było go śledzić
    int pacmanY;

    Direction pacmanDirectory;
    Direction pacmanDirectoryFuture;

    private TrackPacman trackPacman;

    public Ghost(int x, int y, int ghostNumber) {
        this.ghostNumber = ghostNumber;
        this.startX = toPixelsX(x);
        this.startY = toPixelsY(y);
        this.x = toPixelsX(x);
        this.y = toPixelsY(y);
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
        pacmanDirectory = direction;
    }

    public void pushPacmanDirectorFuture(Direction direction) {
        pacmanDirectoryFuture = direction;
    }

    public boolean isBase() {
        System.out.println("xPositon " + this.x + "yPosition " + this.y);
        if (this.y <= 180 && this.y > 140) {
            if ((180 <= this.x) && (200 > this.x)) {
                direction = Direction.RIGHT;
                System.out.println("tutaj xPositon" + this.x + " a toPixel" + toPixelsX(8) + " xPosition9  " + toPixelsX(9));
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
    }

    public void setFearTimeLeft() {
        this.fearTimeLeft = 15; //jeżeli 15 sekund trwa power-up
    }


    public void tick() {
        if (!isBase()) {
            System.out.println("nie bazie");
            trackPacman.trackPacman();// dopracowania
            if (!teleport()) {
                if (canMoveDirectorFutureAndDirectory()) {
                    direction = Direction.DOWN;
                    directionFuture = Direction.RIGHT;
                    if (canMoveDirectorFutureAndDirectory()) {
                        direction = Direction.UP;
                        directionFuture = Direction.LEFT;
                    }
                }
            }
        } else {
            System.out.println("jestem bazie ");
        }

        setSpeed(direction);

        if (fearTimeLeft > 0) {
            fearTimeLeft -= (double) 1 / 60;
        }

        if (deadTimeLeft > 0) {
            deadTimeLeft -= (double) 1 / 60;
            if (deadTimeLeft <= 0)
                alive = true;
        }
        
    }

    public void render(Graphics g) {
        String imgPath;

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
            g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


