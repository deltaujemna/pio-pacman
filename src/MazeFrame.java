import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MazeFrame extends JFrame {
    public final Maze maze = new Maze(this);
    Timer timer = new Timer(true);

    public boolean running = true;

    public static int width = 440;
    public static int height = 460;

    public MazeFrame(String title, boolean fullScreenMode) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if (fullScreenMode)
            setUndecorated(true);
        setContentPane(maze);
        pack();
        setBounds(0, 0, width, height);
        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(new File("Images/pacman_right.png")));
        } catch (IOException e) {
            //ignore
        }
        setResizable(true);
        setMinimumSize(new Dimension(440, 480));
        setVisible(false);

        if (fullScreenMode) {
            FullScreen fullScreen = new FullScreen(this);
            fullScreen.makeFullScreen();
        }

        addKeyListener(new Keys(maze.pacman, this));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                maze.timer.cancel();
                timer.cancel();
                Game.menu.setVisible(true);
            }
        });

        //scheduleWithFixedDelay - lokalne, animacja
        timer.schedule(new TimerTask() {
            public void run() {
                if (running) {
                    getContentPane().repaint();
                }
            }
        }, 0, 1000 / 60);

        //scheduleAtFixedRate - globalne, długoczasowe operacje, stała częstotliwość
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (running) {
                    maze.update();
                }
            }
        }, 0, 1000 / 60);
    }
}