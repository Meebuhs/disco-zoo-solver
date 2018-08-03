package main.ui;

import javafx.scene.layout.GridPane;
import main.discozoosolver.Board;
import main.discozoosolver.Cell;

public class BoardDisplay {
    private SolverApp solver;
    private GridPane display;
    private Board board;

    public BoardDisplay(SolverApp solver, Board board) {
        this.solver = solver;
        this.board = board;
        createDisplay();
        this.display = getDisplay();
    }

    private void createDisplay() {
        GridPane display = new GridPane();
        display.setGridLinesVisible(true);
        for (Cell cell : board.getCells()) {
            display.add(cell.getDisplay().getContents(), cell.getX(), cell.getY());
        }
        this.display = display;
    }

    public void updateDisplay() {
        for (Cell cell : board.getCells()) {
            cell.updateDisplay();
        }
    }

    public GridPane getDisplay() {
        return display;
    }
}