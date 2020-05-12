import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    MazeFrame mazeFrame;

    public Menu(String title) {
        super(title);
        JPanel menuPanel = new JPanel();
        JButton startButton = new JButton("START");
        JButton quitButton = new JButton("QUIT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        menuPanel.setLayout(null);
        menuPanel.setSize(420, 400);
        setContentPane(menuPanel);
        setSize(menuPanel.getSize());
        setLocationRelativeTo(null);
        setResizable(false);

        menuPanel.add(startButton);
        startButton.setBounds(60, 60, 300, 40);

        menuPanel.add(quitButton);
        quitButton.setBounds(60, 260, 300, 40);

        setVisible(true);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeFrame = new MazeFrame("Pacman");
                mazeFrame.setVisible(true);
                setVisible(false);
            }
        });
    }
}
