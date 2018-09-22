package ui;

import discozoosolver.Board;
import discozoosolver.Cell;
import javafx.scene.layout.GridPane;

public class BoardDisplay {
    private GridPane display;
    private Board board;

    public BoardDisplay(Board board) {
        this.board = board;
        createDisplay();
        this.display = getDisplay();
    }

    private void createDisplay() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        for (Cell cell : board.getCells()) {
            gridPane.add(cell.getDisplay().getContents(), cell.getX(), cell.getY());
        }
        this.display = gridPane;
    }

    public void updateDisplay() {
        for (Cell cell : board.getCells()) {
            cell.updateDisplay();
        }
    }

    GridPane getDisplay() {
        return display;
    }
}