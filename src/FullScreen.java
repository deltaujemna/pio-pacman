import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class FullScreen {
    private MazeFrame mazeFrame;

    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();  //fullscreen exclusive
    GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();  //fullscreen exclusive

    public FullScreen(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
    }
    public void makeFullScreen() {
        if (!graphicsDevice.isFullScreenSupported ()) {
            throw new UnsupportedOperationException ("Fullscreen mode is unsupported.");
        }
        graphicsDevice.setFullScreenWindow(mazeFrame);
    }
}