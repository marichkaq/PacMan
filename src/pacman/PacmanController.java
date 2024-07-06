package pacman;

import utils.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PacmanController extends KeyAdapter {
    private PacmanModel model;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public PacmanController(PacmanModel model, PacmanView view) {
        this.model = model;
        view.addKeyListener(this);
        view.setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                upPressed = true;
                model.setDirection(Direction.UP);
                model.setMoving(true);
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                model.setDirection(Direction.DOWN);
                model.setMoving(true);
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                model.setDirection(Direction.LEFT);
                model.setMoving(true);
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                model.setDirection(Direction.RIGHT);
                model.setMoving(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
        }
        updateDirection();
    }

    private void updateDirection() {
        if(upPressed){
            model.setDirection(Direction.UP);
            model.setMoving(true);
        } else if (downPressed) {
            model.setDirection(Direction.DOWN);
            model.setMoving(true);
        } else if (leftPressed) {
            model.setDirection(Direction.LEFT);
            model.setMoving(true);
        } else if (rightPressed) {
            model.setDirection(Direction.RIGHT);
            model.setMoving(true);
        } else {
            model.setMoving(false);
        }
    }
}