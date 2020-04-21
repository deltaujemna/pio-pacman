import java.awt.Rectangle;
import java.awt.Graphics;

public abstract class Entity {

    final int BOARD_START_X = 24;
    final int BOARD_START_Y = 37;

    int width;
    int height;
    int x;
    int y;

    abstract public void render(Graphics g);
    abstract public void tick();

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // zamienia długość z pikseli na komórki
    int toCells(int value) {
        return value / 20;
    }

    // zamienia długość z komórki na piksele
    int toPixels(int value) {
        return value * 20;
    }

}