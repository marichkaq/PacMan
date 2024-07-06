package upgrade;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpgradeView extends JPanel {
    private UpgradeModel model;
    private final int cellSize = 20;
    private BufferedImage doublePointsImage;
    private BufferedImage invulnerabilityImage;
    private BufferedImage extraLifeImage;
    private BufferedImage ghostFreezeImage;
    private BufferedImage speedUpImage;

    public UpgradeView(UpgradeModel model){
        this.model = model;
        setPreferredSize(new Dimension(cellSize-1, cellSize-1));
        setOpaque(false);
        loadImages();
    }

    private void loadImages() {
        try{
            doublePointsImage = ImageIO.read(new File("images/fruits/cherry.png"));
            invulnerabilityImage = ImageIO.read(new File("images/fruits/apple.png"));
            extraLifeImage = ImageIO.read(new File("images/fruits/strawberry.png"));
            ghostFreezeImage = ImageIO.read(new File("images/fruits/melon.png"));
            speedUpImage = ImageIO.read(new File("images/fruits/peach.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawUpgrade(Graphics g){
        int x = model.getX() * cellSize;
        int y = model.getY() * cellSize;
        BufferedImage upgradeImage = switch (model.getType()){
            case DOUBLE_POINTS -> doublePointsImage;
            case INVULNERABILITY -> invulnerabilityImage;
            case EXTRA_LIFE -> extraLifeImage;
            case GHOST_FREEZE -> ghostFreezeImage;
            case SPEED_UP -> speedUpImage;
        };
        g.drawImage(upgradeImage, x, y, cellSize, cellSize, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawUpgrade(g);
    }

    public UpgradeModel getModel() {
        return model;
    }
}
