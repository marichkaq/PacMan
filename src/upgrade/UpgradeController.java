package upgrade;

import game.GameController;
import game.GameModel;
import ghost.GhostView;

public class UpgradeController {
    private GameModel gameModel;
    private GameController gameController;

    public UpgradeController(GameController gameController) {
        this.gameController = gameController;
        this.gameModel = gameController.getModel();
    }

    public synchronized  void applyUpgrade(UpgradeModel upgrade) {
        UpgradeModel.Type type = upgrade.getType();
        String message = "";
        switch (type) {
            case DOUBLE_POINTS:
                gameModel.setScoreMultiplier(2);
                message = "Double Points Activated!";
                break;
            case INVULNERABILITY:
                gameModel.getPacmanModel().setInvulnerable(true);
                message = "Invulnerability Activated!";
                break;
            case EXTRA_LIFE:
                gameModel.setLives(gameModel.getLives() + 1);
                gameController.getView().updateLives(gameModel.getLives());
                message = "Extra Life Gained!";
                break;
            case GHOST_FREEZE:
                gameModel.setGhostsFrozen(true);
                for (GhostView ghostView : gameController.getView().getGhostViews()) {
                    ghostView.repaint();
                }
                message = "Ghosts Frozen!";
                break;
            case SPEED_UP:
                gameModel.getPacmanModel().setSpeedUp(true);
                message = "Speed Boost Activated!";
                break;
        }
        gameController.showUpgradeMessage(message);
        startUpgradeEffectThread(upgrade);
    }

    private void startUpgradeEffectThread(UpgradeModel upgrade){
        UpgradeEffectThread effectThread = new UpgradeEffectThread(upgrade, gameModel, gameController);
        effectThread.start();
    }
}
