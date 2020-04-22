public class Game implements Runnable {
    public MazeFrame mazeFrame;
    public Menu menu;

    Ghost ghost = new Ghost(1, 1, 1);

    Pacman pacman;

    public Game() {
        mazeFrame = new MazeFrame("Pacman");
        menu = new Menu("Pacman Menu", mazeFrame);

        Entity.setInsets(mazeFrame.getInsets());
        pacman = new Pacman(0, 0, 20, 1);
        ghost.pushBoard(mazeFrame.getBoard());
        pacman.pushBoard(mazeFrame.getBoard());
    }

    public void render() {
        ghost.render(mazeFrame.getGraphics());
        pacman.render(mazeFrame.getGraphics());
        mazeFrame.repaint();
    }

    public void renderMaze() {

    }

    public void update() {
        ghost.x++;
        pacman.tick();
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
