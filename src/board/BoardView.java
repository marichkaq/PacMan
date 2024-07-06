package board;

import colors.Colors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardView extends JPanel {
    private final BoardModel boardModel;
    private final int cellSize = 20;
    private BufferedImage dotImage;


    public BoardView(BoardModel boardModel) {
        this.boardModel = boardModel;
        updateSize();
        loadDotImage();
    }

    private void updateSize(){
        int dimension = boardModel.getMaze().length;
        setPreferredSize(new Dimension(dimension * cellSize, dimension * cellSize));
        revalidate();
    }

    private void loadDotImage(){
        try{
            File file = new File("images/dot.png");
            dotImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze(g);
        drawDots(g);
    }

    private void drawMaze(Graphics g){
        int[][] maze = boardModel.getMaze();
        int cellSize = getWidth() / maze.length;
        for (int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze.length; j++){
                if(maze[i][j] == MazeGeneratorService.WALL){
                    g.setColor(Colors.wallColor);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
                else{
                    g.setColor(Colors.pathColor);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    private void drawDots(Graphics g){
        int cellSize = getWidth() / boardModel.getMaze().length;
        for (int i = 0; i < boardModel.getMaze().length; i++){
            for(int j = 0; j < boardModel.getMaze().length; j++){
                if(boardModel.hasPoint(j, i)){

                    int dotSize = (int) (cellSize / 1.5);
                    int x = j * cellSize + (cellSize - dotSize) / 2;
                    int y = i * cellSize + (cellSize - dotSize) / 2;
                    g.drawImage(dotImage, x, y, dotSize, dotSize, this);
                }
            }
        }
    }
}
