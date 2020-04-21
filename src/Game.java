public class Game implements Runnable {
    public Maze maze;
    public Menu menu;

    public Game() {
        maze = new Maze("Pacman");
        menu = new Menu("Pacman Menu", maze);
    }

    public void update() {

    }

    public void render() {
        if(maze.visible == true) {
            maze.repaint();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000.0 / 60;
        double changeInSeconds = 0;

        while (true) {
            long now = System.nanoTime();
            changeInSeconds += (now - lastTime) / nanoSecondConversion;

            while (changeInSeconds >= 1) {
                update();
                changeInSeconds--;
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
