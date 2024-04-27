package MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private JButton newGameButton;
    private JButton highScoresButton;
    private JButton exitButton;
    private JLabel headerImageLabel;


    public MainMenuView() {
        super("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initializeHeaderImage();
        initializeButtons();
        setVisible(true);
    }

    public void initializeHeaderImage(){
        ImageIcon headerImage = new ImageIcon("Images/header.png");
        headerImageLabel = new JLabel(headerImage);
        add(headerImageLabel, BorderLayout.NORTH);
    }

    private void initializeButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.setOpaque(false);
        newGameButton = new JButton("New Game");
        buttonPanel.add(newGameButton);

        JPanel lowerButtonsPanel = new JPanel(new GridLayout(1, 2));
        lowerButtonsPanel.setOpaque(false);
        highScoresButton = new JButton("High Scores");
        exitButton = new JButton("Exit");
        lowerButtonsPanel.add(highScoresButton);
        lowerButtonsPanel.add(exitButton);

        buttonPanel.add(lowerButtonsPanel);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public void addNewGameButtonListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void addHighScoresButtonListener(ActionListener listener) {
        highScoresButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
}
