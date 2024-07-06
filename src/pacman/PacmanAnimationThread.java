package pacman;

import javax.swing.*;

public class PacmanAnimationThread extends Thread{
    private PacmanModel model;
    private PacmanView view;
    private boolean running;

    public PacmanAnimationThread(PacmanModel model, PacmanView view) {
        this.model = model;
        this.view = view;
    }

    public void start(){
        running = true;
        new Thread(this).start();
    }

    public void stopAnimation(){
        running = false;
    }

    @Override
    public void run() {
        while (running){
            try {
                Thread.sleep(250);
                synchronized (model){
                    model.toggleMouth();
                }
                SwingUtilities.invokeLater(()-> view.repaint());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
