package game;

import board.BoardModel;
import board.MazeGeneratorService;
import ghost.GhostModel;
import pacman.PacmanModel;
import upgrade.UpgradeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private BoardModel boardModel;
    private PacmanModel pacmanModel;
    private List<GhostModel> ghostModels;
    private List<UpgradeModel> upgrades;
    private int score;
    private int lives;
    private long time;
    private int scoreMultiplier;
    private boolean ghostsFrozen;

    public GameModel(BoardModel.Size size) {
        this.boardModel = new BoardModel(size);
        this.score = 0;
        this.lives = 3;
        this.time = 0;
        this.upgrades = new ArrayList<>();
        this.scoreMultiplier = 1;
        this.ghostsFrozen = false;
        initPacman();
        initGhosts();
    }

    private void initGhosts() {
        String[] ghostImages = {"images/blinky.png", "images/inky.png", "images/pinky.png", "images/clyde.png"};
        ghostModels = new ArrayList<>();
        for(String imagePath : ghostImages){
            ghostModels.add(new GhostModel(imagePath, boardModel.getMaze()));
        }
    }

    private void initPacman(){
        pacmanModel = new PacmanModel(boardModel.getMaze());
    }

    public boolean checkCollisionWithGhosts(){
        for(GhostModel ghost : ghostModels){
            if(pacmanModel.getX() == ghost.getX() && pacmanModel.getY() == ghost.getY()){
                return true;
            }
        }
        return false;
    }

    public synchronized void setGhostsFrozen(boolean frozen) {
        this.ghostsFrozen = frozen;
        for (GhostModel ghost : ghostModels) {
            if (frozen){
                ghost.setGhostImg(ghost.getFrozenGhostImg());
            } else {
                ghost.setGhostImg(ghost.getNormalGhostImg());
            }
        }
    }

    public synchronized boolean areGhostsFrozen() {
        return ghostsFrozen;
    }

    public List<GhostModel> getGhostModels() {
        return ghostModels;
    }

    public PacmanModel getPacmanModel() {
        return pacmanModel;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore(int points) {
        this.score += points * scoreMultiplier;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<UpgradeModel> getUpgrades() {
        return upgrades;
    }
    public void setScoreMultiplier(int scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }
}
