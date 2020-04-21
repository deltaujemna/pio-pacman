import java.awt.Rectangle;
import java.awt.Graphics;

public abstract class Entity {
    int width;
    int height;
    int x;
    int y;

    abstract public void render(Graphics g);
    abstract public void tick();

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}