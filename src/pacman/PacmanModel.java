package pacman;

import board.MazeGeneratorService;
import utils.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PacmanModel {
    private int x, y;
    private Direction direction;
    private boolean mouthOpen;
    private boolean moving;
    private boolean invulnerable;
    private boolean speedUp;
    private int speed;

    private static final int NORMAL_SPEED = 130;
    private static final int BOOSTED_SPEED = 60;

    private BufferedImage pacmanMouseClosed;
    private BufferedImage pacmanMouseOpened;


    public PacmanModel(int[][] maze){
        this.direction = Direction.RIGHT;
        this.mouthOpen = true;
        this.moving = false;
        this.invulnerable = false;
        this.speedUp = false;
        this.speed = NORMAL_SPEED;
        setPositionToRandom(maze);

        try{
            pacmanMouseClosed = ImageIO.read(new File("images/pacmanMouseClosed.png"));
            pacmanMouseOpened = ImageIO.read(new File("images/pacmanMouseOpen.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void move(int[][] maze) {
        int nextX = x;
        int nextY = y;

        switch (direction) {
            case UP -> nextY--;
            case DOWN -> nextY++;
            case LEFT -> nextX--;
            case RIGHT -> nextX++;
        }

        if (canMoveTo(nextX, nextY, maze)) {
            x = nextX;
            y = nextY;
        }
    }

    private boolean canMoveTo(int nextX, int nextY, int[][] maze) {
        return nextX >= 0 && nextY >= 0 && nextX < maze[0].length && nextY < maze.length && maze[nextY][nextX] == MazeGeneratorService.PATH;
    }

    public void setPositionToRandom(int[][] maze) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(maze[0].length);
            y = random.nextInt(maze.length);
        } while (maze[y][x] != MazeGeneratorService.PATH);
        this.x = x;
        this.y = y;
    }

    public void setSpeedUp(boolean speedUp) {
        this.speedUp = speedUp;
        this.speed = speedUp ? BOOSTED_SPEED : NORMAL_SPEED;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public void toggleMouth() {
        mouthOpen = !mouthOpen;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public BufferedImage getCurrentImage(){
        if (mouthOpen) {
            return pacmanMouseOpened;
        } else {
            return pacmanMouseClosed;
        }
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }

}
