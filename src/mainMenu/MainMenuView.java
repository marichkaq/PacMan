package mainMenu;

import colors.Colors;
import utils.BackgroundPanel;
import utils.RoundButton;

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
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel("images/mainMenuBackground.png");
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        initializeHeaderImage();
        initializeButtons();
        setVisible(true);
    }

    public void initializeHeaderImage(){
        ImageIcon headerImage = new ImageIcon("images/header.png");
        headerImageLabel = new JLabel(headerImage);
        getContentPane().add(headerImageLabel, BorderLayout.NORTH);
    }

    private void initializeButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(Box.createVerticalStrut(20));

        newGameButton = new RoundButton("New game", Colors.primaryButtonColor, Colors.primaryButtonPressesColor, Colors.textWhite);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(200, 50));

        highScoresButton = new RoundButton("High Scores", Colors.secondaryButtonColor, Colors.secondaryButtonPressedColor, Colors.textBlack);
        highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoresButton.setMaximumSize(new Dimension(180, 40));

        exitButton = new RoundButton("Exit", Colors.secondaryButtonColor, Colors.secondaryButtonPressedColor, Colors.textBlack);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(180, 40));

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(highScoresButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        getContentPane().add(buttonPanel, BorderLayout.CENTER);
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
