package pacman;

import game.GameController;
import game.GameModel;
import game.GameView;

import javax.swing.*;

public class PacmanMoveThread implements Runnable {
    private boolean running;
    private final PacmanModel model;
    private final PacmanView view;
    private final int[][] maze;
    private final GameModel gameModel;
    private final GameView gameView;
    private final GameController gameController;

    public PacmanMoveThread(PacmanModel model, PacmanView view, int[][] maze, GameController gameController) {
        this.model = model;
        this.view = view;
        this.maze = maze;
        this.gameController = gameController;
        this.gameModel = gameController.getModel();
        this.gameView = gameController.getView();
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stopMoving() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if(model.isMoving()){
                    synchronized (gameModel){
                        model.move(maze);
                        checkAndCollectPoint();
                        gameController.checkForWin();
                    }
                    SwingUtilities.invokeLater(view::repaint);
                }
                Thread.sleep(model.getSpeed());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void checkAndCollectPoint(){
        int x = model.getX();
        int y = model.getY();
        if(gameModel.getBoardModel().hasPoint(x, y)){
            gameModel.getBoardModel().removePoint(x, y);
            gameModel.incrementScore(100);
            SwingUtilities.invokeLater(() -> gameView.updateScore(gameModel.getScore()));
            gameView.getBoardView().repaint();
        }
    }
}

