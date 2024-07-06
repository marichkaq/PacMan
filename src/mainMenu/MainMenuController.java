package mainMenu;

import board.BoardModel;
import game.GameController;
import highScore.HighScoreModel;
import highScore.HighScoreView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController {
    private MainMenuView view;
    private HighScoreModel highScoreModel;

    public MainMenuController() {
        this.view = new MainMenuView();
        this.highScoreModel = new HighScoreModel();
        this.view.addNewGameButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        this.view.addHighScoresButtonListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showHighScores();
            }
        });
        this.view.addExitButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
    }

    private void startGame(){
        BoardSizeDialog boardSizeDialog = new BoardSizeDialog(view);
        boardSizeDialog.setVisible(true);
        BoardModel.Size size = boardSizeDialog.getSelectedSize();

        view.dispose();

        new GameController(size, highScoreModel);
    }

    private void showHighScores(){
        view.dispose();
        new HighScoreView(view, highScoreModel).setVisible(true);
    }

    private void exit(){
        System.exit(0);
    }
}
