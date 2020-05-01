import java.awt.*;

public class Dot extends CollectableEntity {

    private boolean renderable = true;

    @Override
    public void tick() {

    }

    @Override
    public void pickup(Pacman p) {
        super.pickup(p);
        renderable = false;
    }

    @Override
    public void render(Graphics g) {
        final int size = 10;

        if (renderable) {
            g.setColor(Color.ORANGE);
            g.fillOval(toPixelsX(x) + (Maze.cellSize - size) / 2, toPixelsY(y) + (Maze.cellSize - size) / 2, size, size);
        }
    }

    public Dot(int x, int y) {
        super(x, y);
    }

}
