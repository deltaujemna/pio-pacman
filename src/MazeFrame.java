import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class MazeFrame extends JFrame {
    public final Maze maze = new Maze(this);
    Timer timer = new Timer(); // to chyba można jako daemon (true w argumencie)

    public boolean running = true;

    public int frameWidth;

//    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();  //fullscreen exclusive
//    GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();  //fullscreen exclusive

    public MazeFrame(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(maze);
        pack();
        setBounds(0, 0, 440, 460); //większy rozmiar / full screen exclusive mode
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(false);

        //graphicsDevice.setFullScreenWindow(this); //fullscreen exclusive
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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