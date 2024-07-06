package upgrade;

import board.MazeGeneratorService;
import game.GameController;
import ghost.GhostModel;

import java.util.List;
import java.util.Random;

public class UpgradeGenerationThread implements Runnable{
    private boolean running;
    private List<GhostModel> ghostModels;
    private final int[][] maze;
    private final int delay = 5000;
    private GameController gameController;

    public UpgradeGenerationThread(List<GhostModel> ghostModels, int[][] maze, GameController gameController) {
        this.ghostModels = ghostModels;
        this.maze = maze;
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
        Random random = new Random();
        while (running){
            try{
                Thread.sleep(delay);
                if(random.nextInt(100) < 25){
                    GhostModel ghost = ghostModels.get(random.nextInt(ghostModels.size()));
                    int x = ghost.getX();
                    int y = ghost.getY();
                    if(maze[y][x] == MazeGeneratorService.PATH){
                        UpgradeModel.Type type = UpgradeModel.Type.values()[random.nextInt(UpgradeModel.Type.values().length)];
                        UpgradeModel upgrade = new UpgradeModel(x, y, type);
                        synchronized (gameController) {
                            gameController.addUpgrade(upgrade);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
