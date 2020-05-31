package pacman;

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
    }

    @Override
    public void render(Graphics g) {
        String imgPath = "Images/cherry.png";
        if (renderable) {
            try {
                g.drawImage(ImageIO.read(new File(imgPath)), (int) ((this.x + Maze.deltaX) * Maze.scale),
                        (int) ((this.y + Maze.deltaY) * Maze.scale), (int) (this.width * Maze.scale), (int) (this.height * Maze.scale), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Fruit(int x, int y) {
        super(x, y);
        super.x = toPixels(x);
        super.y = toPixels(y);
        this.points = 200;
        this.renderable = true;
        width = 17;
        height = 17;
    }
}