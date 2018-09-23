package ui;

import discozoosolver.Board;
import discozoosolver.Cell;
import javafx.scene.layout.GridPane;

/**
 * Display class which contains the grid of cells.
 */
public class BoardDisplay {
    private GridPane display;
    private Board board;

    /**
     * Sole constructor for BoardDisplay which populates the grid with the cells from the provided board.
     *
     * @param board The board for which to create a display.
     */
    public BoardDisplay(Board board) {
        this.board = board;
        this.display = createDisplay();
    }

    /**
     * Creates and populates a gridpane and returns it. The contents of each cell's display are placed in their
     * respective cell of the grid.
     *
     * @return The gridpane display element.
     */
    private GridPane createDisplay() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        for (Cell cell : board.getCells()) {
            gridPane.add(cell.getDisplay().getContents(), cell.getX(), cell.getY());
        }
        return gridPane;
    }

    /**
     * Updates the display for each cell.
     */
    public void updateDisplay() {
        for (Cell cell : board.getCells()) {
            cell.updateDisplay();
        }
    }

    /**
     * @return The display element.
     */
    GridPane getDisplay() {
        return display;
    }
}