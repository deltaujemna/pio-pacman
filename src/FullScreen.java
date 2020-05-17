import javax.swing.JFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class FullScreen {
    private MazeFrame mazeFrame;

    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

    public FullScreen(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
    }
    public void makeFullScreen() {
        if (!graphicsDevice.isFullScreenSupported ()) {
            mazeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            graphicsDevice.setFullScreenWindow(mazeFrame);
        }
    }
}