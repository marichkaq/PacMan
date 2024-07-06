package utils;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    private final Color pressedColor;
    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;

    public RoundButton(String text, Color background, Color pressedColor, Color textColor) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(background);
        setForeground(textColor);
        setFont(new Font("Arial", Font.BOLD, 16));
        this.pressedColor = pressedColor;
    }

    @Override
    protected void paintComponent(Graphics g){
        if (getModel().isPressed()){
            g.setColor(pressedColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, ARC_WIDTH, ARC_HEIGHT);
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
        return shape.contains(x, y);
    }
}

