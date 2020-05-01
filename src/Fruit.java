import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Fruit extends CollectableEntity {

    @Override
    public void pickup(Pacman p) {
        // TODO: zrobić
    }

    @Override
    public void tick() {
        // TODO: zrobić
    }

    @Override
    public void render(Graphics g) {
        final int size = 10;
        String imgPath="Images/fruit.png";
        if (renderable) {
            try {
                g.drawImage(ImageIO.read(new File(imgPath)), this.x, this.y, this.width, this.height, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Fruit(int x, int y) {
        super(x, y);
    }

}