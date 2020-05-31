package pacman;

import javax.swing.*;
import java.awt.*;

public class FullScreen {
    private final MazeFrame mazeFrame;

    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

    public FullScreen(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
    }

    public void makeFullScreen() {
        if (!graphicsDevice.isFullScreenSupported()) {
            mazeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            graphicsDevice.setFullScreenWindow(mazeFrame);
        }
    }
}