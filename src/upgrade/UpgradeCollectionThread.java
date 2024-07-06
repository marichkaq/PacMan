package upgrade;

import game.GameController;

public class UpgradeCollectionThread implements Runnable {
    private boolean running;
    private final GameController gameController;
    private final int checkDelay = 100;

    public UpgradeCollectionThread(GameController gameController) {
        this.gameController = gameController;
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
               synchronized (gameController){
                   gameController.checkForUpgradeCollection();
               }
                Thread.sleep(checkDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
