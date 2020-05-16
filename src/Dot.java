import java.awt.*;

public class Dot extends CollectableEntity {

    @Override
    public void tick() {

    }

    @Override
    public void pickup(Pacman p) {
        if (renderable)
            super.pickup(p);
        renderable = false;
    }

    @Override
    public void render(Graphics g) {
        final int size = (int) (7 * Maze.scale);

        if (renderable) {
            g.setColor(Color.ORANGE);
            g.fillOval((int) ((toPixelsX(x) + (Maze.cellSize - size) / 2 + Maze.deltaX) * Maze.scale),
                    (int) ((toPixelsY(y) + (Maze.cellSize - size) / 2 + Maze.deltaY) * Maze.scale), size, size);
        }
    }

    public Dot(int x, int y) {
        super(x, y);
        this.points = 10;
        this.renderable = true;
    }

}
