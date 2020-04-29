import java.util.Timer;
import java.util.TimerTask;

public class Game implements Runnable {
    public MazeFrame mazeFrame;
    public Menu menu;
    Timer timer = new Timer();
    TimerTask tick;

    Ghost ghost;
    Pacman pacman;

    private final CollectableEntity[][] dots;

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

        dots = new CollectableEntity[19][19];
        for(int i = 0; i < dots.length; i++) {
            for(int j = 0; j < dots[i].length; j++) {
                if(mazeFrame.activeDots(i, j))
                    dots[i][j] = new Dot(j, i);
            }
        }

        ghost.pushBoard(mazeFrame.getBoard());
        pacman.pushBoard(mazeFrame.getBoard());
        pacman.pushDots(dots);

        mazeFrame.setKeyListener(new Keys(pacman));
    }

    public void render() {
        ghost.render(mazeFrame.getContentPane().getGraphics());
        pacman.render(mazeFrame.getContentPane().getGraphics());

        for(int i = 0; i < dots.length; i++) {
            for(int j = 0; j < dots[i].length; j++) {
                if(dots[i][j] != null && mazeFrame.activeDots(i, j))
                    dots[i][j].render(mazeFrame.getContentPane().getGraphics());
            }
        }

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
