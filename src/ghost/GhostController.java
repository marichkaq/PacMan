package ghost;

import pacman.PacmanModel;

import java.util.List;

public class GhostController {
    private GhostModel model;
    private GhostView view;
    private int[][] maze;
    private PacmanModel pacmanModel;
    private List<GhostModel> otherGhosts;


    public GhostController(GhostModel model, GhostView view, int[][] maze, PacmanModel pacmanModel, List<GhostModel> otherGhosts) {
        this.model = model;
        this.view = view;
        this.maze = maze;
        this.pacmanModel = pacmanModel;
        this.otherGhosts = otherGhosts;
    }

    public void moveGhost() {
        model.move(maze, pacmanModel.getX(), pacmanModel.getY(), otherGhosts);
        view.repaint();
    }

    public GhostView getView() {
        return view;
    }

    public GhostModel getModel() {
        return model;
    }
}
