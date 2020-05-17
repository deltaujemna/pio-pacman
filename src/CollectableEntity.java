public abstract class CollectableEntity extends Entity {
    int points;
    boolean renderable;

    public void pickup(Pacman p) {
        p.addScore(points);
    }

    public CollectableEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }
}