package game;

import colors.Colors;
import highScore.HighScoreModel;
import mainMenu.MainMenuController;
import utils.RoundButton;

import javax.swing.*;
import java.awt.*;

public class GameEndDialog extends JDialog {
    private final int score;
    private final JFrame parentFrame;
    private final String imagePath;
    private final HighScoreModel highScoreModel;

    public GameEndDialog(JFrame parentFrame, int score, String imagePath, HighScoreModel highScoreModel){
        super(parentFrame, true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.score = score;
        this.parentFrame = parentFrame;
        this.imagePath = imagePath;
        this.highScoreModel = highScoreModel;

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Colors.gamePanelColor);

        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(imageLabel);

        JLabel messageLabel = new JLabel("Your score is: " + score);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Colors.textBlack);
        mainPanel.add(Box.createVerticalStrut(1));
        mainPanel.add(messageLabel);

        JLabel questionLabel = new JLabel("Do you want to save your score?");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setForeground(Colors.textBlack);
        mainPanel.add(questionLabel);

        RoundButton saveButton = new RoundButton("Yes!", Colors.primaryButtonColor, Colors.primaryButtonPressesColor, Colors.textWhite);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> saveScore());

        RoundButton exitButton = new RoundButton("Exit", Colors.secondaryButtonColor, Colors.secondaryButtonPressedColor, Colors.textBlack);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> exitGame());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.gamePanelColor);
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(exitButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
        setLocationRelativeTo(parentFrame);
    }

    private void saveScore(){
        dispose();
        String playerName = JOptionPane.showInputDialog(this, "Enter your nickname:", "Save Score", JOptionPane.PLAIN_MESSAGE);
        if (playerName != null && !playerName.trim().isEmpty()) {
            highScoreModel.addHighScore(playerName, score);
            JOptionPane.showMessageDialog(this, "Score saved successfully!");
            new MainMenuController();
        }
    }

    private void exitGame() {
        dispose();
        new MainMenuController();
    }


}
