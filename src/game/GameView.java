package game;

import board.BoardView;
import colors.Colors;
import ghost.GhostModel;
import ghost.GhostView;
import mainMenu.MainMenuController;
import pacman.PacmanView;
import upgrade.UpgradeModel;
import upgrade.UpgradeView;
import utils.RoundButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JFrame {
    private JLayeredPane layeredPane;
    private BoardView boardView;
    private PacmanView pacManView;
    private final List<GhostView> ghostViews;
    private final List<UpgradeView> upgradeViews;
    private final GameModel model;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel upgradeMessageLabel;
    private JPanel livesContainer;
    private JButton exitGameButton;
    private final GameController gameController;
    int boardSize;

    public GameView(GameController gameController) {
        super("Pacman game");
        this.gameController = gameController;
        this.model = gameController.getModel();
        this.ghostViews = new ArrayList<>();
        this.upgradeViews = new ArrayList<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initBoard();
        initTopPanel();
        initBottomPanel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initBoard(){
        layeredPane = new JLayeredPane();
        int cellSize = 20;
        int dimensions = model.getBoardModel().getMaze().length;
        boardSize = dimensions * cellSize;
        layeredPane.setPreferredSize(new Dimension(boardSize, boardSize));

        boardView = new BoardView(model.getBoardModel());
        pacManView = new PacmanView(model.getPacmanModel());
        boardView.setBounds(0, 0, boardSize, boardSize);
        pacManView.setBounds(0, 0, boardSize, boardSize);

        layeredPane.add(boardView, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(pacManView, JLayeredPane.MODAL_LAYER);

        for(GhostModel ghostModel : model.getGhostModels()){
            GhostView ghostView = new GhostView(ghostModel);
            ghostViews.add(ghostView);
            ghostView.setBounds(0, 0, boardSize, boardSize);
            layeredPane.add(ghostView, JLayeredPane.POPUP_LAYER);
        }

        add(layeredPane, BorderLayout.CENTER);
    }

    private void initTopPanel(){
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Colors.gamePanelColor);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Colors.gamePanelColor);

        timeLabel = new JLabel("Time: " + formatTime(model.getTime()));
        timeLabel.setForeground(Colors.textBlack);
        leftPanel.add(timeLabel);

        scoreLabel = new JLabel("Score: " + model.getScore());
        scoreLabel.setForeground(Colors.textBlack);
        leftPanel.add(scoreLabel);

        topPanel.add(leftPanel, BorderLayout.WEST);

        upgradeMessageLabel = new JLabel();
        upgradeMessageLabel.setHorizontalAlignment(JLabel.RIGHT);
        upgradeMessageLabel.setForeground(Colors.textBlack);
        upgradeMessageLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
        topPanel.add(upgradeMessageLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private void initBottomPanel(){
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Colors.gamePanelColor);

        livesContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        livesContainer.setBackground(Colors.gamePanelColor);
        updateLives(model.getLives());
        bottomPanel.add(livesContainer, BorderLayout.WEST);

        exitGameButton = new RoundButton("Exit game", Colors.secondaryButtonColor, Colors.secondaryButtonPressedColor, Colors.textBlack);
        exitGameButton.addActionListener(e -> exitToMainMenu());
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.setBackground(Colors.gamePanelColor);
        buttonContainer.add(exitGameButton);
        bottomPanel.add(buttonContainer, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void exitToMainMenu(){
        gameController.endGame();
        this.dispose();
        new MainMenuController();
    }

    public void updateLives(int lives) {
        livesContainer.removeAll();
        for(int i = 0; i < lives; i++){
            JLabel life = new JLabel(new ImageIcon("images/heart.png"));
            livesContainer.add(life);
        }
        livesContainer.revalidate();
        livesContainer.repaint();
    }

    private String formatTime(long time){
        long min = time / 60;
        long sec = time % 60;
        return String.format("%02d:%02d", min, sec);
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public PacmanView getPacmanView(){
        return pacManView;
    }

    public void updateTime(long time) {
        timeLabel.setText("Time: " + formatTime(time));
    }

    public void updateScore(int score){
        scoreLabel.setText("Score: " + score);
    }

    public List<GhostView> getGhostViews() {
        return ghostViews;
    }

    public void addUpgrade(UpgradeModel upgradeModel) {
        UpgradeView upgradeView = new UpgradeView(upgradeModel);
        upgradeViews.add(upgradeView);
        upgradeView.setBounds(0, 0, boardSize, boardSize);
        layeredPane.add(upgradeView, JLayeredPane.PALETTE_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void removeUpgrade(UpgradeModel upgradeModel) {
        UpgradeView toRemove = null;
        for (UpgradeView upgradeView : upgradeViews) {
            if (upgradeView.getModel().equals(upgradeModel)) {
                toRemove = upgradeView;
                break;
            }
        }
        if (toRemove != null) {
            upgradeViews.remove(toRemove);
            layeredPane.remove(toRemove);
            layeredPane.revalidate();
            layeredPane.repaint();
        }
    }

    public void showUpgradeMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            upgradeMessageLabel.setText(message);
            upgradeMessageLabel.revalidate();
            upgradeMessageLabel.repaint();
        });
    }

    public void clearUpgradeMessage() {
        SwingUtilities.invokeLater(() -> {
            upgradeMessageLabel.setText("");
            upgradeMessageLabel.revalidate();
            upgradeMessageLabel.repaint();
        });
    }
}
