import java.awt.*;

public abstract class Entity {

    private static Insets insets;

    int width;
    int height;
    int x;
    int y;

    abstract public void render(Graphics g);

    abstract public void tick();

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public static void setInsets(Insets newInsets) {
        insets = newInsets;
    }

    // zamienia długość z pikseli na komórki bez uwzględnienia ramki okna
    int toCells(int value) {
        return value / 20;
    }

    // zamienia długość z komórki na piksele bez uwzględnienia ramki okna
    int toPixels(int value) {
        return value * 20;
    }

    // zamienia długość z pikseli na komórki dla X
    int toCellsX(int value) {
        return (value - insets.left) / 20 - 1;
    }

    // zamienia długość z pikseli na komórki dla Y
    int toCellsY(int value) {
        return (value - insets.top) / 20 - 1;
    }

    // zamienia długość z komórki na piksele dla X
    int toPixelsX(int value) {
        return (value + 1) * 20 + insets.left;
    }

    // zamienia długość z komórki na piksele dla Y
    int toPixelsY(int value) {
        return (value + 1) * 20 + insets.top;
    }


}