import javax.swing.*;

public class MazeFrame extends JFrame {
    public MazeFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(new Maze());
        this.pack();
        this.setBounds(0, 0, 440, 460);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
    }
}