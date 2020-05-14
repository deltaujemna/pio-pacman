import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Fruit extends CollectableEntity {

    @Override
    public void pickup(Pacman p) {
        if (renderable)
            super.pickup(p);
        renderable = false;
    }

    @Override
    public void tick() {
        // TODO: zrobić
    }

    @Override
    public void render(Graphics g) {
        String imgPath = "Images/cherry.png";
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
        super.x = toPixelsX(x);
        super.y = toPixelsX(y);
        this.points = 100;
        this.renderable = true;
        width = 17;
        height = 17;
    }

}