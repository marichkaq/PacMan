package upgrade;

import game.GameController;
import game.GameModel;
import ghost.GhostView;

public class UpgradeEffectThread extends Thread{
    private final UpgradeModel upgrade;
    private final GameModel gameModel;
    private final GameController gameController;
    private final long duration;

    public UpgradeEffectThread(UpgradeModel upgrade, GameModel gameModel, GameController gameController) {
        this.upgrade = upgrade;
        this.gameModel = gameModel;
        this.gameController = gameController;
        this.duration = upgrade.getDuration();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        removeUpgradeEffect();
    }

    private synchronized void removeUpgradeEffect() {
        UpgradeModel.Type type = upgrade.getType();
        switch (type) {
            case SPEED_UP:
                gameModel.getPacmanModel().setSpeedUp(false);
                break;
            case DOUBLE_POINTS:
                gameModel.setScoreMultiplier(1);
                break;
            case GHOST_FREEZE:
                gameModel.setGhostsFrozen(false);
                for (GhostView ghostView : gameController.getView().getGhostViews()) {
                    ghostView.repaint();
                }
                break;
            case INVULNERABILITY:
                gameModel.getPacmanModel().setInvulnerable(false);
                break;
        }
        gameController.clearUpgradeMessage();
    }
}
