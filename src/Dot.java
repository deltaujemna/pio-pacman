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
        final int size = 7;

        if (renderable) {
            g.setColor(Color.ORANGE);
            g.fillOval((int) ((toPixelsX(x) + Maze.deltaX + (Maze.cellSize - size) / 2) * Maze.scale),
                    (int) ((toPixelsY(y) + Maze.deltaY + (Maze.cellSize - size) / 2) * Maze.scale), (int) (size * Maze.scale), (int) (size * Maze.scale));
        }
    }

    public Dot(int x, int y) {
        super(x, y);
        this.points = 10;
        this.renderable = true;
    }
}
