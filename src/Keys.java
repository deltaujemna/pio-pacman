import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener {

    private final Pacman pacman;

    private final int keyUp = 38;
    private final int keyDown = 40;
    private final int keyLeft = 37;
    private final int keyRight = 39;

    public Keys(Pacman pacman) {
        this.pacman = pacman;
    }

    @Override
    public void keyTyped(KeyEvent key) {
        // to ma być puste
    }

    @Override
    public void keyPressed(KeyEvent key) {
        switch(key.getKeyCode()) {
            case keyUp:
                pacman.setDirection(LivingEntity.Direction.UP);
                break;
            case keyDown:
                pacman.setDirection(LivingEntity.Direction.DOWN);
                break;
            case keyLeft:
                pacman.setDirection(LivingEntity.Direction.LEFT);
                break;
            case keyRight:
                pacman.setDirection(LivingEntity.Direction.RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        // to też ma być puste
    }
}
