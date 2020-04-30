import java.util.Timer;
import java.util.TimerTask;

public class Game implements Runnable {
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
//        ghost.render(mazeFrame.getContentPane().getGraphics());
//        pacman.render(mazeFrame.getContentPane().getGraphics());
//
//        for(int i = 0; i < dots.length; i++) {
//            for(int j = 0; j < dots[i].length; j++) {
//                if(dots[i][j] != null && mazeFrame.activeDots(i, j))
//                    dots[i][j].render(mazeFrame.getContentPane().getGraphics());
//            }
//        }

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
        Thread gameThread = new Thread(game);
        gameThread.start(); // threads for each "sprite"?


    }
}
