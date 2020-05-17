import javax.swing.*;
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
        setResizable(false);
        setVisible(false);

        FullScreen fullScreen = new FullScreen(this);
        fullScreen.makeFullScreen();

        addKeyListener(new Keys(maze.pacman));

        // <=> scheduleWithFixedDelay - lokalne, animacja
        timer.schedule(new TimerTask() {
            public void run() { //jeden wątek do wszystkiego
                if (running) {
                    getContentPane().repaint();
                    maze.update();
                }
            }
        }, 0, 1000 / 60);
    }
}