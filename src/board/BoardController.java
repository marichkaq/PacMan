package board;

public class BoardController {
    private final BoardModel boardModel;
    private final BoardView boardView;

    public BoardController(BoardModel boardModel, BoardView boardView) {
        this.boardModel = boardModel;
        this.boardView = boardView;
    }

}
