public class Game implements Runnable {
    public MazeFrame mazeFrame;
    public Menu menu;

    public Game() {
        mazeFrame = new MazeFrame("Pacman");
        menu = new Menu("Pacman Menu", mazeFrame);
    }

    public void render() {
        mazeFrame.repaint();
    }

    public void update() {

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000.0;
        double changeInSeconds = 0;

        while (true) {
            long now = System.nanoTime();
            changeInSeconds += (now - lastTime) / nanoSecondConversion;

            while (changeInSeconds >= 1.0 / 60.0) {
                update();
                changeInSeconds = 0;
            }
            render();
            lastTime = now;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
