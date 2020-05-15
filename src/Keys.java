import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener {

    private final Pacman pacman;

    public Keys(Pacman pacman) {
        this.pacman = pacman;
    }

    @Override
    public void keyTyped(KeyEvent key) {
        // to ma być puste
    }

    @Override
    public void keyPressed(KeyEvent key) {
        final int keyUp = 38;
        final int keyDown = 40;
        final int keyLeft = 37;
        final int keyRight = 39;

        final int escape = 27;


        switch (key.getKeyCode()) {
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
            case escape:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        // to też ma być puste
    }
}