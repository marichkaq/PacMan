package game;

import board.BoardController;
import board.BoardModel;
import ghost.GhostController;
import ghost.GhostModel;
import ghost.GhostMoveThread;
import ghost.GhostView;
import highScore.HighScoreModel;
import mainMenu.MainMenuController;
import pacman.PacmanAnimationThread;
import pacman.PacmanController;
import pacman.PacmanModel;
import pacman.PacmanMoveThread;
import upgrade.UpgradeCollectionThread;
import upgrade.UpgradeController;
import upgrade.UpgradeGenerationThread;
import upgrade.UpgradeModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private GameModel model;
    private GameView view;
    private BoardController boardController;
    private PacmanController pacmanController;
    private List<GhostController> ghostControllers;
    private PacmanAnimationThread pacmanAnimationThread;
    private TimerThread timerThread;
    private PacmanMoveThread pacmanMoveThread;
    private List<GhostMoveThread> ghostMoveThreads;
    private CollisionCheckThread collisionCheckThread;
    private HighScoreModel highScoreModel;
    private UpgradeGenerationThread upgradeGenerationThread;
    private UpgradeController upgradeController;
    private UpgradeCollectionThread upgradeCollectionThread;


    public GameController(BoardModel.Size size, HighScoreModel highScoreModel) {
        model = new GameModel(size);
        this.highScoreModel = new HighScoreModel();
        view = new GameView(this);
        boardController = new BoardController(model.getBoardModel(), view.getBoardView());
        pacmanController = new PacmanController(model.getPacmanModel(), view.getPacmanView());
        upgradeController = new UpgradeController( this);
        ghostControllers = new ArrayList<>();
        ghostMoveThreads = new ArrayList<>();

        initGhosts();
        initThreads();

        timerThread.start();
        pacmanAnimationThread.start();
        pacmanMoveThread.start();
        collisionCheckThread.start();
        upgradeGenerationThread.start();
        upgradeCollectionThread.start();

        for(GhostMoveThread ghostThread : ghostMoveThreads){
            ghostThread.start();
        }
    }

    private void initGhosts(){
        List<GhostModel> ghostModels = model.getGhostModels();
        for (int i = 0; i < ghostModels.size(); i++) {
            GhostModel ghostModel =ghostModels.get(i);
            GhostView ghostView = view.getGhostViews().get(i);
            GhostController ghostController = new GhostController(ghostModel, ghostView, model.getBoardModel().getMaze(), model.getPacmanModel(), ghostModels);
            ghostControllers.add(ghostController);
            GhostMoveThread ghostMoveThread = new GhostMoveThread(ghostController, model);
            ghostMoveThreads.add(ghostMoveThread);
        }
    }

    private void initThreads(){
        timerThread = new TimerThread(this);
        pacmanAnimationThread = new PacmanAnimationThread(model.getPacmanModel(), view.getPacmanView());
        pacmanMoveThread = new PacmanMoveThread(model.getPacmanModel(), view.getPacmanView(), model.getBoardModel().getMaze(), this);
        collisionCheckThread = new CollisionCheckThread(this);
        upgradeGenerationThread = new UpgradeGenerationThread(model.getGhostModels(), model.getBoardModel().getMaze(), this);
        upgradeCollectionThread = new UpgradeCollectionThread(this);
    }

    public void endGame(){
        timerThread.stop();
        pacmanAnimationThread.stopAnimation();
        pacmanMoveThread.stopMoving();
        collisionCheckThread.stopChecking();
        upgradeGenerationThread.stop();
        upgradeCollectionThread.stop();
        for (GhostMoveThread ghostMoveThread : ghostMoveThreads) {
            ghostMoveThread.stop();
        }
        view.dispose();
    }

    public void updateTime() {
        SwingUtilities.invokeLater(() -> {
            view.updateTime(model.getTime());
        });
    }

    public void checkForWin(){
        if(model.getBoardModel().allPointsCollected()){
            endGame();
            SwingUtilities.invokeLater(() -> new GameEndDialog(view, model.getScore(), "images/win.png", highScoreModel).setVisible(true));
        }
    }

    public void checkForCollision(){
        if(!model.getPacmanModel().isInvulnerable() && model.checkCollisionWithGhosts()){
            model.setLives(model.getLives() - 1);
            if(model.getLives() <= 0){
                endGame();
                SwingUtilities.invokeLater(() -> new GameEndDialog(view, model.getScore(), "images/gameOver.png", highScoreModel).setVisible(true));
            } else {
                model.getPacmanModel().setPositionToRandom(model.getBoardModel().getMaze());
                view.updateLives(model.getLives());
            }
        }
    }

    public synchronized void checkForUpgradeCollection() {
        PacmanModel pacman = model.getPacmanModel();
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();

        UpgradeModel collectedUpgrade = null;
        for (UpgradeModel upgrade : model.getUpgrades()) {
            if (upgrade.getX() == pacmanX && upgrade.getY() == pacmanY) {
                collectedUpgrade = upgrade;
                break;
            }
        }
        if (collectedUpgrade != null){
            model.getUpgrades().remove(collectedUpgrade);
            removeUpgrade(collectedUpgrade);
            upgradeController.applyUpgrade(collectedUpgrade);
        }
    }


    public void addUpgrade(UpgradeModel upgradeModel) {
        synchronized (this) {
            model.getUpgrades().add(upgradeModel);
            view.addUpgrade(upgradeModel);
        }
    }

    public void removeUpgrade(UpgradeModel upgradeModel) {
        synchronized (this) {
            model.getUpgrades().remove(upgradeModel);
            view.removeUpgrade(upgradeModel);
        }
    }

    public void showUpgradeMessage(String message) {
        SwingUtilities.invokeLater(() -> view.showUpgradeMessage(message));
    }

    public void clearUpgradeMessage() {
        SwingUtilities.invokeLater(() -> view.clearUpgradeMessage());
    }

    public GameModel getModel() {
        return model;
    }

    public GameView getView() {
        return view;
    }

}


