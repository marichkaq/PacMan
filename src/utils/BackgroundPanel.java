package utils;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImg;
    public BackgroundPanel(String imgPath){
        super();
        backgroundImg = new ImageIcon(imgPath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
