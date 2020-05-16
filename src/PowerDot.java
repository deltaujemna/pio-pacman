import java.awt.*;

public class PowerDot extends CollectableEntity {

    public void activatePowerup(Pacman p) {
        p.activatePowerup();
    }

    @Override
    public void tick() {

    }

    @Override
    public void pickup(Pacman p) {
        if (renderable) {
            super.pickup(p);
            activatePowerup(p);
        }
        renderable = false;
    }

    @Override
    public void render(Graphics g) {
        final int size = (int) (13 * Maze.scale);
        if (renderable) {
            g.setColor(Color.ORANGE);
            g.fillOval((int) ((toPixelsX(x) + (Maze.cellSize - size) / 2 + Maze.deltaX) * Maze.scale),
                    (int) ((toPixelsY(y) + (Maze.cellSize - size) / 2 + Maze.deltaY) * Maze.scale), size, size);
        }
    }

    public PowerDot(int x, int y) {
        super(x, y);
        this.points = 20;
        this.renderable = true;
    }
}
