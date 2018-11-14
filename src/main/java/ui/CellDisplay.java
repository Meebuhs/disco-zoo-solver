package ui;

import discozoosolver.Cell;
import discozoosolver.Constants;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

import static discozoosolver.Constants.CELL_HEIGHT_FACTOR;

/**
 * Display class which is responsible for rendering the contents of a single cell. The main responsibility of a cell is
 * to display the animals which could be found within and can have one of four states:
 * - Regular: This is the base state and the cell will display up to three animals and a miss icon.
 * - Finalised: When the user has confirmed the contents of a cell it is finalised and will display only the animal
 * or miss icon.
 * - Known: When the solver has determined the contents of a cell but the user has not actioned it, the cell will
 * be highlighted gold and only the animal it contains is displayed.
 * - Priority: If the cell is currently the non-known/finalised cell most likely to contain an animal it is
 * highlighted green and all options are shown.
 */
public class CellDisplay {
    private GridPane contents;
    private SolverApp solver;
    private Cell cell;

    /**
     * Sole constructor for CellDisplay. It is linked to the solver which created it and the cell which contains all
     * state logic.
     *
     * @param solver The solver which created this celldisplay.
     * @param cell   The cell which this display represents.
     */
    public CellDisplay(SolverApp solver, Cell cell) {
        contents = new GridPane();
        this.solver = solver;
        this.cell = cell;
        setPrefSize();
    }

    void setPrefSize() {
        contents.setPrefSize(this.solver.height * CELL_HEIGHT_FACTOR, this.solver.height * CELL_HEIGHT_FACTOR);
        for (Node item : contents.getChildren()) {
            ImageView iv = (ImageView) item;
            double factor = ((cell.getFinalised()) || cell.getKnown()) ? 1 : 0.5;
            iv.setFitWidth(this.solver.height * CELL_HEIGHT_FACTOR * factor);
        }
    }

    /**
     * @return The display element for this cell.
     */
    GridPane getContents() {
        return contents;
    }

    /**
     * Renders the appropriate view given the current state of the cell.
     */
    public void populateCell() {
        clearContents();
        if (cell.getKnown()) {
            renderKnown();
        } else if (cell.getFinalised()) {
            renderFinalised();
        } else {
            renderAnimals();
        }
    }

    /**
     * Renders the known view of the cell. This occurs when the solver has determined the contents of the cell and the user
     * has not yet actioned it. The cell will be rendered with a gold background and a single icon representing the
     * animal.
     */
    private void renderKnown() {
        List<String> animals = cell.getAnimals();
        ImageView iv = createImageView(animals.get(0));
        contents.add(iv, 0, 0);
        contents.setStyle("-fx-background-color: #ffc107;");
    }

    /**
     * Renders the finalised view of the cell. In this state, the cell is not highlighted but a single icon representing
     * either the animal that was found or the miss icon to show it is empty.
     */
    private void renderFinalised() {
        List<String> animals = cell.getAnimals();
        String filename;
        if (animals.isEmpty()) {
            filename = Constants.BLANK_DARK;
        } else {
            filename = animals.get(0);
        }
        ImageView iv = createImageView(filename);
        contents.add(iv, 0, 0);
    }

    /**
     * Renders the priority and standard views of the cell. In these states there will be at least one animal and a
     * miss icon rendered in a grid within the cell. If the cell is a priority, it is also highlighted green.
     */
    private void renderAnimals() {
        List<String> animals = cell.getAnimals();
        for (int i = 0; i < animals.size(); i++) {
            String animal = animals.get(i);
            ImageView iv = createImageView(animal);
            contents.add(iv, i % 2, (i > 1) ? 1 : 0);
        }
        ImageView iv = createImageView(Constants.BLANK_DARK);
        contents.add(iv, animals.size() % 2, (animals.size() > 1) ? 1 : 0);
        if (cell.getPriority()) {
            contents.setStyle("-fx-background-color: #66bb6a;");
        }
    }

    /**
     * Clears the cell display removing any icons and resetting the background colour.
     */
    public void clearContents() {
        contents.getChildren().clear();
        contents.setStyle("");
    }

    /**
     * Creates an imageview for the given filename. The size is determined by the cell's state and the onclick method
     * confirms the appropriate hit/miss with the board.
     *
     * @param filename The filename of the image.
     * @return The resulting imageview.
     */
    private ImageView createImageView(String filename) {
        if (filename.equals("blank")) {
            filename = "blank-dark";
        }
        String path = (filename.equals(Constants.BLANK_DARK) ? String.format("elements/%s.png", filename) : String
                .format("animals/%s.png", filename));
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Image image = new Image(classLoader.getResourceAsStream(path));

        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        iv.setPickOnBounds(true);
        iv.setOpacity(0.9);

        if (!(cell.getFinalised())) {
            if (filename.equals(Constants.BLANK_DARK)) {
                iv.setOnMouseClicked((Event event) -> {
                    cell.confirmMiss();
                    solver.updateBoardDisplay();
                });
            } else {
                String animal = filename;
                iv.setOnMouseClicked((Event event) -> {
                    cell.confirmHit(animal);
                    solver.updateBoardDisplay();
                });
            }
        }

        if ((cell.getFinalised()) || cell.getKnown()) {
            iv.setFitWidth(this.solver.height * CELL_HEIGHT_FACTOR);
        } else {
            iv.setFitWidth(this.solver.height * CELL_HEIGHT_FACTOR * 0.5);
        }

        return iv;
    }
}
