public class Game implements Runnable {
    public MazeFrame mazeFrame;
    public Menu menu;

    Ghost ghost = new Ghost(50, 50, 1);
    Ghost ghost2 = new Ghost(100, 100, 2);

    public Game() {
        mazeFrame = new MazeFrame("Pacman");
        menu = new Menu("Pacman Menu", mazeFrame);
    }

    public void render() {
        ghost.render(mazeFrame.getGraphics());
        ghost2.render(mazeFrame.getGraphics());

        mazeFrame.repaint();
    }

    public void renderMaze() {

    }

    public void update() {

        // Aby działało poruszanie się ducha w prawą stronę to: - tymczasowe
        ghost.decideDirection();
        ghost.speed = 1; //albo jakiś algorytm, albo jak przy stałych fpsach trzeba ustawić na oko

        ghost.tick();

        ghost2.decideDirection();
        ghost2.speed = 1;
        ghost2.tick();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000.0;
        double changeInSeconds = 0;

        this.renderMaze();

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
