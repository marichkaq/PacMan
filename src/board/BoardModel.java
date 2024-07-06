package board;

public class BoardModel {
     public enum Size{
        SMALL, MEDIUM, LARGE, XLARGE, XXLARGE
    }
    private final Size boardSize;
    public int[][] maze;
    public boolean[][] points;

    public BoardModel(Size boardSize) {
        this.boardSize = boardSize;
        this.maze = MazeGeneratorService.generateMaze(boardSize);
        initPoints();
    }

    public void initPoints(){
        int dimensions = maze.length;
        points = new boolean[dimensions][dimensions];
        for(int i = 0; i < dimensions; i++){
            for(int j = 0; j < dimensions; j++){
                points[i][j] = (maze[i][j] == MazeGeneratorService.PATH);
            }
        }
    }

    public boolean hasPoint(int x, int y){
        return points[y][x];
    }

    public void removePoint(int x, int y){
        points[y][x] = false;
    }

    public boolean allPointsCollected(){
        for(boolean[] row : points){
            for(boolean point : row){
                if(point){
                    return false;
                }
            }
        }
        return true;
    }

    public Size getBoardSize() {
        return boardSize;
    }

    public int[][] getMaze() {
        return maze;
    }
}
