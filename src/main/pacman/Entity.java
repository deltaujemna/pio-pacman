package pacman;

import java.awt.*;

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

    // zamienia długość z pikseli na komórki
    int toCells(int value) {
        return value / 20 - 1;
    }

    // zamienia długość z komórki na piksele
    int toPixels(int value) {
        return (value + 1) * 20;
    }

}
