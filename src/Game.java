import java.util.Timer;
import java.util.TimerTask;

public class Game implements Runnable {
    public MazeFrame mazeFrame;
    public Menu menu;
    Timer timer = new Timer();
    TimerTask tick;

    Ghost ghost;
    Pacman pacman;

    public Game() {
        mazeFrame = new MazeFrame("Pacman");
        menu = new Menu("Pacman Menu", mazeFrame);

        //Jest wywoływane przez timer w określonych odstępach czasu
        tick = new TimerTask() {
            @Override
            public void run() {
                render();
                update();
            }
        };

        ghost = new Ghost(1, 1, 1);
        pacman = new Pacman(0, 0, 20, 1);
        ghost.pushBoard(mazeFrame.getBoard());
        pacman.pushBoard(mazeFrame.getBoard());

        mazeFrame.setKeyListener(new Keys(pacman));
    }

    public void render() {
        ghost.render(mazeFrame.getContentPane().getGraphics());
        pacman.render(mazeFrame.getContentPane().getGraphics());
        mazeFrame.getContentPane().repaint();
    }

    public void update() {
        //ghost.x++;
        pacman.tick();
    }


    @Override
    public void run() {
        timer.scheduleAtFixedRate(tick, 0, (long) (1000.0 / 60.0));
    }

    //import java.util.Timer;
    //import java.util.concurrent.ScheduledThreadPoolExecutor;


    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start(); // threads for each "sprite"?


    }
}
