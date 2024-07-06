package game;

public class TimerThread implements Runnable {
    private boolean running;
    private long startTime;
    private int elapsedTime;
    private final GameModel model;
    private final GameController controller;

    public TimerThread(GameController controller) {
        this.controller = controller;
        this.model = controller.getModel();
    }

    public void start(){
        startTime = System.currentTimeMillis();
        running = true;
        new Thread(this).start();
    }

    public void stop(){
        running = false;
    }

    @Override
    public void run() {
        while(running){
            try{
                Thread.sleep(1000);
                elapsedTime = (int) ((System.currentTimeMillis() - startTime)/ 1000);
                synchronized (model){
                    model.setTime(elapsedTime);
                }
                controller.updateTime();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

}
