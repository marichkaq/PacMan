package game;

public class CollisionCheckThread implements Runnable{
    private boolean running;
    private final GameController gameController;
    private final int checkDelay = 100;

    public CollisionCheckThread(GameController gameController) {
        this.gameController = gameController;
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stopChecking() {
        running = false;
    }

    @Override
    public void run() {
        while(running){
            try{
                synchronized (gameController){
                    gameController.checkForCollision();
                }
                Thread.sleep(checkDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
