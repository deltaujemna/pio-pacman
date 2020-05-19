import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {
    private MazeFrame mazeFrame;

    public Menu(String title) {
        super(title);
        JPanel menuPanel = new JPanel();
        JButton startButton = new JButton("START");
        JButton quitButton = new JButton("QUIT");
        String[] fullScreenComboBoxItems = new String[2];
        fullScreenComboBoxItems[0] = "Pełny ekran";
        fullScreenComboBoxItems[1] = "Okno";
        JComboBox fullScreenComboBox = new JComboBox(fullScreenComboBoxItems);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            setIconImage(ImageIO.read(new File("Images/pacman_right.png")));
        } catch (IOException e) {
        }
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

        menuPanel.add(fullScreenComboBox);
        fullScreenComboBox.setBounds(135, 120, 150, 25);

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
                if (fullScreenComboBox.getSelectedIndex() == 0)
                    mazeFrame = new MazeFrame("Pacman", true);
                else
                    mazeFrame = new MazeFrame("Pacman", false);
                mazeFrame.setVisible(true);
                setVisible(false);
            }
        });
    }
}