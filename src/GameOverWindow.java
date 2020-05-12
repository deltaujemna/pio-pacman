import javax.swing.*;

public class GameOverWindow extends JFrame {

//TODO - sugeruję usunąć tę klasę i zostawić tylko message boxa z komunikatem o końcu gry - MM
    MazeFrame mazeFrame;

    public GameOverWindow(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setBounds(0, 0, 420, 460);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }
}
