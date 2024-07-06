package ghost;

import game.GameController;
import game.GameModel;

import javax.swing.*;

public class GhostMoveThread implements Runnable{
    private boolean running;
    private final GhostController controller;
    private final int stepDelay = 450;
    private final GameModel gameModel;


    public GhostMoveThread(GhostController controller, GameModel gameModel) {
        this.controller = controller;
        this.gameModel = gameModel;
    }

    public void start(){
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while(running){
            try{
                if (!gameModel.areGhostsFrozen()) {
                    synchronized (controller.getModel()) {
                        controller.moveGhost();
                    }
                }
                SwingUtilities.invokeLater(() -> controller.getView().repaint());
                Thread.sleep(stepDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
