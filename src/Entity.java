import java.awt.Rectangle;

public interface Entity {
    int width = 0;
    int height = 0;
    int x = 0;
    int y = 0;

    public void render();
    public void tick();
    public Rectangle getBounds();
}