public class Game implements Runnable {
    public Menu menu;

    public Game() {
        menu = new Menu("Pacman Menu");
    }

    @Override
    public void run() {
        // tu nie musi być nic
        // to jest chyba dowód na to że źle zrobiliśmy multithreading XD
    }

    //import java.util.Timer;
    //import java.util.concurrent.ScheduledThreadPoolExecutor;

    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start(); // threads for each "sprite"?
    }
}
