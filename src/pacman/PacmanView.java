package pacman;

import utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class PacmanView extends JPanel {
    private PacmanModel model;
    private final int cellSize = 20;

    public PacmanView(PacmanModel model) {
        this.model = model;
        setPreferredSize(new Dimension(cellSize - 1, cellSize -1));
        setOpaque(false);
    }

    public void drawPacman(Graphics g){
        int x = model.getX() * cellSize;
        int y = model.getY() * cellSize;
        BufferedImage pacmanImage = model.getCurrentImage();
        BufferedImage rotatedImage = rotateImage(pacmanImage, model.getDirection());
        g.drawImage(rotatedImage, x, y, cellSize, cellSize, this);
    }

    private BufferedImage rotateImage(BufferedImage image, Direction direction) {
        double rotationRequired = 0;
        switch (direction){
            case UP -> rotationRequired = Math.toRadians(270);
            case DOWN -> rotationRequired = Math.toRadians(90);
            case LEFT -> rotationRequired = Math.toRadians(180);
            case RIGHT -> rotationRequired = Math.toRadians(0);
        }
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPacman(g);
    }
}