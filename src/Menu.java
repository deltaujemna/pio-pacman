import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JPanel menuPanel;
    private JButton startButton;
    private JButton quitButton;

    MazeFrame mazeFrame;

    public Menu(String title, MazeFrame mazeFrame) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(menuPanel);
        this.pack();
        this.setBounds(0, 0, 420, 460);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);

        this.mazeFrame = mazeFrame;

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Naciśnięcie start powinno powodować pojawienie się okna Maze
                mazeFrame.setVisible(true);
                setVisible(false);
            }
        });
    }
}
