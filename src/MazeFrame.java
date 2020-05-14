import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class MazeFrame extends JFrame {
    public final Maze maze = new Maze(this);
    Timer timer = new Timer(); // to chyba można jako daemon (true w argumencie)

    public boolean running = true;

    public MazeFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(maze);
        this.pack();
        this.setBounds(0, 0, 440, 460); //większy rozmiar / full screen exclusive mode
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);

        addKeyListener(new Keys(maze.pacman));

            // <=> scheduleWithFixedDelay - lokalne, animacja
        timer.schedule(new TimerTask() {
            public void run() { //jeden wątek do wszystkiego
                if(running) {
                    getContentPane().repaint();
                    maze.update();
                }
            }
        }, 0, 1000 / 60);
    }
}