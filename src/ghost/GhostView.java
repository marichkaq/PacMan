package ghost;

import utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class GhostView extends JPanel {
    private GhostModel model;
    private final int cellSize = 20;

    public GhostView(GhostModel model){
        this.model = model;
        setPreferredSize(new Dimension(cellSize - 1, cellSize -1));
        setOpaque(false);
    }

    public void drawGhost(Graphics g){
        int x = model.getX() * cellSize;
        int y = model.getY() * cellSize;
        BufferedImage ghostImage = model.getGhostImage();
        g.drawImage(ghostImage, x, y, cellSize, cellSize, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGhost(g);
    }
}
