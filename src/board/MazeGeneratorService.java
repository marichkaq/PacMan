package board;

import java.util.*;

public class MazeGeneratorService {
    protected static final int WALL = 1;
    public static final int PATH = 0;

    public static int[][] generateMaze(BoardModel.Size size) {
        int dimensions = sizeToDimensions(size);
        int[][] maze = new int[dimensions][dimensions];

        for (int i = 0; i < dimensions; i++) {
            for(int j = 0; j < dimensions; j++){
                maze[i][j] = WALL;
            }
        }
        generateMazeRecursive(maze, 1, 1);
        return maze;
    }

    private static int sizeToDimensions(BoardModel.Size size) {
        return switch (size) {
            case SMALL -> 15;
            case MEDIUM -> 21;
            case LARGE -> 25;
            case XLARGE -> 31;
            case XXLARGE -> 35;
        };
    }

    private static void generateMazeRecursive(int[][] maze, int x, int y) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Collections.shuffle(Arrays.asList(directions));

        for (int[] dir : directions) {
            int nx = x + 2 * dir[0];
            int ny = y + 2 * dir[1];

            if (nx >= 0 && ny >= 0 && nx < maze.length && ny < maze[0].length && maze[nx][ny] == WALL) {
                maze[nx][ny] = PATH;
                maze[x + dir[0]][y + dir[1]] = PATH;
                generateMazeRecursive(maze, nx, ny);
            }
        }
    }

}


