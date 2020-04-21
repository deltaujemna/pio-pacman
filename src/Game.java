public class Game {

    public Game() {
        Maze maze = new Maze("Pacman");
        Menu menu = new Menu("Pacman Menu", maze);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
