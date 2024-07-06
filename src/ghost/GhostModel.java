package ghost;

import board.MazeGeneratorService;
import game.GameModel;
import utils.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GhostModel {
    private int x, y;
    private Direction direction;
    private BufferedImage ghostImg;
    private BufferedImage normalGhostImg;
    private BufferedImage frozenGhostImg;
    private Random random;

    public GhostModel(String imagePath, int[][] maze) {
        random = new Random();
        this.direction = getRandomDirection();
        setPositionToRandom(maze);
        try{
            normalGhostImg = ImageIO.read(new File(imagePath));
            frozenGhostImg = ImageIO.read(new File("images/frozenGhost.png"));
            ghostImg = normalGhostImg;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setPositionToRandom(int[][] maze) {
        int x, y;
        do {
            x = random.nextInt(maze[0].length);
            y = random.nextInt(maze.length);
        } while (maze[y][x] != MazeGeneratorService.PATH);
        this.x = x;
        this.y = y;
    }

    private Direction getRandomDirection() {
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

    protected void move(int[][] maze, int pacmanX, int pacmanY, List<GhostModel> otherGhosts){
        int nextX = x;
        int nextY = y;

        if (pacmanX > x && canMoveTo(x + 1, y, maze, otherGhosts)) {
            nextX = x + 1;
        } else if (pacmanX < x && canMoveTo(x - 1, y, maze, otherGhosts)) {
            nextX = x - 1;
        } else if (pacmanY > y && canMoveTo(x, y + 1, maze, otherGhosts)) {
            nextY = y + 1;
        } else if (pacmanY < y && canMoveTo(x, y - 1, maze, otherGhosts)) {
            nextY = y - 1;
        } else {
            direction = getRandomDirection();
            switch (direction) {
                case UP -> nextY = y - 1;
                case DOWN -> nextY = y + 1;
                case LEFT -> nextX = x - 1;
                case RIGHT -> nextX = x + 1;
            }
        }

        if (canMoveTo(nextX, nextY, maze, otherGhosts)) {
            x = nextX;
            y = nextY;
        }
    }

    private boolean canMoveTo(int nextX, int nextY, int[][] maze, List<GhostModel> otherGhosts) {
        if (nextX < 0 || nextY < 0 || nextX >= maze[0].length || nextY >= maze.length || maze[nextY][nextX] != MazeGeneratorService.PATH) {
            return false;
        }
        for(GhostModel ghost : otherGhosts){
            if(ghost.getX() == nextX && ghost.getY() == nextY){
                return false;
            }
        }
        return true;
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

    public BufferedImage getGhostImage() {
        return ghostImg;
    }

    public void setGhostImg(BufferedImage img) {
        this.ghostImg = img;
    }

    public BufferedImage getNormalGhostImg() {
        return normalGhostImg;
    }

    public BufferedImage getFrozenGhostImg() {
        return frozenGhostImg;
    }
}
