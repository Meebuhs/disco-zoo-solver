package ui;

import discozoosolver.Cell;
import discozoosolver.Constants;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class CellDisplay {
    private GridPane contents;
    private SolverApp solver;
    private Cell cell;

    public CellDisplay(SolverApp solver, Cell cell) {
        contents = new GridPane();
        contents.setPrefSize(90, 90);
        this.solver = solver;
        this.cell = cell;
    }

    GridPane getContents() {
        return contents;
    }

    public void populateCell() {
        clearContents();
        if (cell.getKnown()) {
            renderKnown();
        } else if (cell.getFinalised()) {
            renderFinalised();
        } else {
            renderAnimals();
        }
        // System.out.println(cell.getX() + ", " + cell.getY() + " " + cell.getCount() + " " + cell.getPriority());
    }

    private void renderKnown() {
        List<String> animals = cell.getAnimals();
        ImageView iv = createImageView(animals.get(0));
        contents.add(iv, 0, 0);
        contents.setStyle("-fx-background-color: #ffc107;");
    }

    private void renderFinalised() {
        List<String> animals = cell.getAnimals();
        String filename;
        if (animals.size() == 0) {
            // Empty
            filename = Constants.BLANK_DARK;
        } else {
            // Animal
            filename = animals.get(0);
        }
        ImageView iv = createImageView(filename);
        contents.add(iv, 0, 0);
    }

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

    public void clearContents() {
        contents.getChildren().clear();
        contents.setStyle("");
    }

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
        iv.setFitWidth(45);
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
            iv.setFitWidth(90);
        }
        return iv;
    }
}
