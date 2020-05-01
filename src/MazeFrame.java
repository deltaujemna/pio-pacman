import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class MazeFrame extends JFrame {

    public final Maze maze = new Maze();
    Timer timer = new Timer(); // to chyba można jako daemon (true w argumencie)

    public MazeFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(maze);
        this.pack();
        this.setBounds(0, 0, 440, 460);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);

        addKeyListener(new Keys(maze.pacman));

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getContentPane().repaint();
                maze.update();
            }
        }, 0, 1000 / 60);
    }

}