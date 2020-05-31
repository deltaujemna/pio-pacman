package pacman;

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
        final int size = 13;
        if (renderable) {
            g.setColor(Color.ORANGE);
            g.fillOval((int) ((toPixels(x) + (Maze.cellSize - size) / 2 + Maze.deltaX) * Maze.scale),
                    (int) ((toPixels(y) + (Maze.cellSize - size) / 2 + Maze.deltaY) * Maze.scale), (int) (size * Maze.scale), (int) (size * Maze.scale));
        }
    }

    public PowerDot(int x, int y) {
        super(x, y);
        this.points = 20;
        this.renderable = true;
    }
}