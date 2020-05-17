import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MazeFrame extends JFrame {
    public final Maze maze = new Maze(this);
    Timer timer = new Timer(); // to chyba można jako daemon (true w argumencie)

    public boolean running = true;

    public static int width = 440;
    public static int height = 460;

    public MazeFrame(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(maze);
        pack();
        setBounds(0, 0, width, height);
        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(new File("Images/pacman_right.png")));
        } catch (IOException e) {
        }
        setResizable(true);
        setMinimumSize(new Dimension(440, 480));
        setVisible(false);

        FullScreen fullScreen = new FullScreen(this);
        fullScreen.makeFullScreen();

        addKeyListener(new Keys(maze.pacman));

        // <=> scheduleWithFixedDelay - lokalne, animacja
        timer.schedule(new TimerTask() {
            //TODO - można rozbić na dwa wątki: repaint() tu gdzie jest, a update() do nowego timer.scheduleAtFixedRate
            public void run() { //jeden wątek do wszystkiego
                if (running) {
                    getContentPane().repaint();
                    maze.update();
                }
            }
        }, 0, 1000 / 60);
    }
}