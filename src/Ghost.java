import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class Ghost extends LivingEntity {
    double fearTimeLeft;
    int points = 200;     //tymczasowo
    int ghostNumber = 1; //jeżeli każdy duch ma inny kolor
    final int size = 20;
    long timeDecideDirection;

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
    }

    public void trackPacman() {

    }

    public void decideDirection() {
        if (System.nanoTime() - timeDecideDirection >= 0.75e9) {
            direction = directionFuture;
            Random rand = new Random();
            int tempDirection = rand.nextInt(4);
            if (tempDirection == 0)
                directionFuture = Direction.RIGHT;
            if (tempDirection == 1)
                directionFuture = Direction.UP;
            if (tempDirection == 2)
                directionFuture = Direction.DOWN;
            if (tempDirection == 3)
                directionFuture = Direction.LEFT;
            timeDecideDirection = System.nanoTime();
        }
    }

    public boolean isFrightened() {
        return this.fearTimeLeft > 0;
    }

    public void die() {
        this.alive = false;
    }

    public void setFearTimeLeft() {
        this.fearTimeLeft = 15000; //jeżeli 15 sekund trwa power-up
    }

    public void tick() {
        decideDirection();
        if (canMove()) {
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

    public void render(Graphics g) {
        String imgPath = "Images/ghost" + ghostNumber + "_right" + ".png";
        if (direction == Direction.RIGHT || directionFuture == Direction.RIGHT)
            imgPath = "Images/ghost" + ghostNumber + "_right" + ".png";
        if (direction == Direction.LEFT || directionFuture == Direction.LEFT)
            imgPath = "Images/ghost" + ghostNumber + "_left" + ".png";

        try {
            g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


