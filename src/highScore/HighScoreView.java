package highScore;

import colors.Colors;
import mainMenu.MainMenuController;
import utils.BackgroundPanel;
import utils.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoreView extends JDialog{
    private HighScoreModel highScoreModel;
    private JFrame parentFrame;

    public HighScoreView(JFrame parentFrame, HighScoreModel highScoreModel) {
        super(parentFrame, true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.highScoreModel = highScoreModel;
        this.parentFrame = parentFrame;

        initUI();
    }

    private void initUI() {
        BackgroundPanel mainPanel = new BackgroundPanel("images/highScoresBackground.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel imageLabel = new JLabel(new ImageIcon("images/highScores.png"));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(imageLabel);

        List<HighScoreModel.HighScore> highScores = highScoreModel.getHighScores();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(HighScoreModel.HighScore highScore : highScores){
            listModel.addElement(highScore.getPlayerName() + " - " + highScore.getScore());
        }

        JList<String> scoreList = new JList<>(listModel);
        scoreList.setBackground(Colors.transparent);
        scoreList.setForeground(Colors.textBlack);

        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        mainPanel.add(scrollPane);

        RoundButton exitButton = new RoundButton("Exit", Colors.primaryButtonColor, Colors.primaryButtonPressesColor, Colors.textWhite);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> exitToMainMenu());

        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(exitButton);

        setContentPane(mainPanel);
        pack();
        setSize(250, 320);
        setLocationRelativeTo(parentFrame);
    }

    private void exitToMainMenu() {
        dispose();
        new MainMenuController();
    }

}