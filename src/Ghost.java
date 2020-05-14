import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Random;
import java.util.concurrent.Future;

public class Ghost extends LivingEntity {
    double fearTimeLeft;
    double deadTimeLeft;
    int points = 200;     //tymczasowo
    int ghostNumber = 1; //jeżeli każdy duch ma inny kolor
    final int size = 20;
    long timeDecideDirection;
    int pacmanX; //aktualna pozycja Pacmana, żeby można było go śledzić
    int pacmanY;
    Direction pacmanDirectory;
    Direction pacmanDirectoryFuture;

    boolean availableDirectoryUp;
    boolean availableDirectoryDown;
    boolean availableDirectoryRight;
    boolean availableDirectoryLeft;

    private  TrackPacman trackPacman;

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

    public void trackPacman() {

        if (ghostNumber == 1) {
            decideDirection1();
        } else if (ghostNumber == 2) {
            decideDirection2();
        } else if (ghostNumber == 3) {
            decideDirection3();
        } else {
            decideDirection4();
        }

    }

    // red ghost
    public void decideDirection1() {
        if (System.nanoTime() - timeDecideDirection >= 0.75e9) {
            direction = directionFuture;
            Random rand = new Random();

            int tempDirection = rand.nextInt(4);
            if (tempDirection == 0) {
                directionFuture = Direction.RIGHT;
                if (tempDirection == 1)
                    directionFuture = Direction.UP;
                if (tempDirection == 2)
                    directionFuture = Direction.DOWN;
                if (tempDirection == 3)
                    directionFuture = Direction.LEFT;
            }
            timeDecideDirection = System.nanoTime();
        }
    }

    // pink ghost
    public void decideDirection2() {
        if (System.nanoTime() - timeDecideDirection >= 0.75e9) {
            directionFuture = pacmanDirectory;
            timeDecideDirection = System.nanoTime();
        }
    }

    // orange ghost
    public void decideDirection3() {
        if (System.nanoTime() - timeDecideDirection >= 0.75e9) {
            findAvailableDirectory();
            boolean isLeft;
            boolean isUp;
            int xDistanceFromPacman = this.x - pacmanX;
            int yDistanceFromPacman = this.y - pacmanY;
            if (xDistanceFromPacman > 0) {
                isLeft = true;
            } else {
                isLeft = false;
            }
            if (yDistanceFromPacman > 0) {
                isUp = true;
            } else {
                isUp = false;
            }
            if (donMoveToBase(xDistanceFromPacman)) {

            } else if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                if (isLeft) {
                    if (availableDirectoryLeft) {
                        direction = Direction.LEFT;
                    } else {
                        moveDownorUp(isUp);
                    }
                } else {
                    if (availableDirectoryRight) {
                        direction = Direction.RIGHT;
                    } else {
                        moveDownorUp(isUp);
                    }
                }
            } else {
                if (isUp) {
                    if (availableDirectoryUp) {
                        direction = Direction.UP;
                    } else {
                        moveLeftorRight(isLeft);
                    }
                } else {
                    if (availableDirectoryDown) {
                        direction = Direction.DOWN;
                    } else {
                        moveLeftorRight(isLeft);
                    }
                }

            }
            directionFuture = direction;
        }
    }

    private void moveLeftorRight(boolean isLeft){
        if (isLeft && availableDirectoryLeft) {
            direction = Direction.LEFT;
        } else if (availableDirectoryRight) {
            direction = Direction.RIGHT;
        }
    }
    private void moveDownorUp(boolean isUp){
        if (isUp && availableDirectoryUp) {
            direction = Direction.UP;
        } else if (availableDirectoryDown) {
            direction = Direction.DOWN;
        }
    }

    // yellow ghost
    public void decideDirection4() {
        if (System.nanoTime() - timeDecideDirection >= 0.75e9) {
            findAvailableDirectory();
            decideDirection3();
            int xDistanceFromPacman = this.x - pacmanX;
            int yDistanceFromPacman = this.y - pacmanY;

            if (donMoveToBase(xDistanceFromPacman)) {

            } else if ((xDistanceFromPacman * xDistanceFromPacman) > (yDistanceFromPacman * yDistanceFromPacman)) {
                if (xDistanceFromPacman > 0 && availableDirectoryLeft) {
                    direction = Direction.LEFT;
                } else if (availableDirectoryRight) {
                    direction = Direction.RIGHT;
                } else if (yDistanceFromPacman > 0 && availableDirectoryUp) {
                    direction = Direction.UP;
                } else if (availableDirectoryDown) {
                    direction = Direction.DOWN;
                }
            } else {
                if (yDistanceFromPacman > 0 && availableDirectoryUp) {
                    direction = Direction.UP;
                } else if (availableDirectoryDown) {
                    direction = Direction.DOWN;
                } else if (xDistanceFromPacman > 0 && availableDirectoryLeft) {
                    direction = Direction.LEFT;
                } else if (availableDirectoryRight) {
                    direction = Direction.RIGHT;
                }
            }
            directionFuture = direction;
        }


    }

    private boolean donMoveToBase(int xDistanceFromPacman) {
        if (this.x == 200 && this.y == 140) {
            if (xDistanceFromPacman > 0 && availableDirectoryLeft) {
                direction = Direction.LEFT;
                return true;
            } else if (availableDirectoryRight) {
                direction = Direction.RIGHT;
                return true;
            }
        }
        return false;
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

    private void findAvailableDirectory() {
        availableDirectoryDown = availableThisDirectory(Direction.DOWN);
        availableDirectoryLeft = availableThisDirectory(Direction.LEFT);
        availableDirectoryRight = availableThisDirectory(Direction.RIGHT);
        availableDirectoryUp = availableThisDirectory(Direction.UP);

    }

    private boolean availableThisDirectory(Direction direction) {
        if (canMoveThisDirection(direction)) {
            System.out.println("to directory jest avaible " + direction);
            return true;
        } else {
            return false;
        }
    }

    public void tick() {
        if (!isBase()) {
            System.out.println("nie bazie");
            trackPacman.trackPacman();// dopracowania
            if (teleport()) {

            } else if (!canMove()) {
                direction = Direction.DOWN;
                directionFuture = Direction.RIGHT;
                if (!canMove()) {
                    direction = Direction.UP;
                    directionFuture = Direction.LEFT;
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


