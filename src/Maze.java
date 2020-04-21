import javax.swing.*;
import javax.swing.JFrame;

public class Maze extends JFrame {
    private JPanel mazePanel = new JPanel();

    public boolean visible = false;

    public Maze(String title) {
        super(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mazePanel);
        this.pack();
        this.setBounds(0, 0, 420, 460);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
    }

    public void render() {

    }

}
