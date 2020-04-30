import java.util.Timer;
import java.util.TimerTask;

public class Game implements Runnable {
    private static Thread gameThread;
    public MazeFrame mazeFrame;
    public Menu menu;
    Timer timer = new Timer();
    TimerTask tick;
    public Game() {
        mazeFrame = new MazeFrame("Pacman");
        menu = new Menu("Pacman Menu", mazeFrame);

        //Jest wywoływane przez timer w określonych odstępach czasu
        tick = new TimerTask() {
            @Override
            public void run() {
                render();
                mazeFrame.maze.update();
            }
        };
        mazeFrame.setKeyListener(new Keys(mazeFrame.maze.pacman));
    }

    public void render() {
        mazeFrame.getContentPane().repaint();
    }

    @Override
    public void run() {
        timer.scheduleAtFixedRate(tick, 0, (long) (1000.0 / 60.0));
    }

    //import java.util.Timer;
    //import java.util.concurrent.ScheduledThreadPoolExecutor;

    public static void main(String[] args) {
        Game game = new Game();
        gameThread = new Thread(game);
        gameThread.start(); // threads for each "sprite"?

    }
}
