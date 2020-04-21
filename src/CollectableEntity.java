import java.awt.Graphics;

public abstract class CollectableEntity extends Entity {

    int points;

    abstract void pickup(Pacman p);

    @Override
    public void render(Graphics g) {
        // TODO: zrobić
    }

}