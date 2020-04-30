import javax.swing.*;
import java.awt.event.KeyListener;

public class MazeFrame extends JFrame {

    private final Maze maze = new Maze();

    public MazeFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(maze);
        this.pack();
        this.setBounds(0, 0, 440, 460);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
    }

    public boolean[][] getBoard() {
        return maze.grid;
    }

    public void setKeyListener(KeyListener keyListener) {
        addKeyListener(keyListener);
    }

    public boolean activeDots(int i, int j) {
        return maze.dots[i][j];
    }

}