import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Ghost extends LivingEntity {
    double fearTimeLeft;
    int points = 200;     //tymczasowo
    int numerDucha = 1;
    final int width = 20;
    final int height = 20;

    public Ghost(int x, int y, int numerDucha) {
        this.x = x;
        this.y = y;
        this.numerDucha = numerDucha;
    }

    public void trackPacman() {

    }

    public void decideDirection() {
        direction = Direction.LEFT;  //tymczasowo
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

    public void render(Graphics g) {
        String imgPath = "Images/duch" + this.numerDucha + ".png";
        try {
            g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


