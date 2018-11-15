package ui;

import discozoosolver.Animal;
import discozoosolver.Block;
import discozoosolver.Board;
import discozoosolver.Cell;
import discozoosolver.GameDataParser;
import discozoosolver.Location;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static discozoosolver.Constants.WINDOW_HEIGHT;
import static discozoosolver.Constants.WINDOW_WIDTH;

/**
 * The SolverApp is the main application class which instantiates and arranges all of the visual elements.
 */
public class SolverApp extends Application {
    private Console console;
    private Map<String, Location> locations;
    private Board board;
    private double height = WINDOW_HEIGHT;
    private BorderPane border;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        locations = GameDataParser.parseData();
        board = new Board(this);

        primaryStage.setTitle("Disco Zoo Solver");

        border = new BorderPane();
        ToolPane toolPane = new ToolPane(this);
        BoardDisplay boardDisplay = board.getBoardDisplay();
        console = new Console();

        border.setTop(toolPane.getToolBar());
        border.setCenter(boardDisplay.getDisplay());
        border.setBottom(console.getDisplay());

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            for (Cell cell : board.getCells()) {
                cell.getDisplay().setPrefSize();
            }
            height = border.getHeight();
        });

        primaryStage.setScene(new Scene(border, WINDOW_WIDTH, height));
        primaryStage.show();
    }

    /**
     * @param message The text to display in the consoles.
     */
    public void setConsole(String message) {
        console.setText(message);
    }

    /**
     * Updates the board display.
     */
    public void updateBoardDisplay() {
        board.updateDisplay();
    }

    /**
     * Starts a new game in the board using the provided location and animals.
     *
     * @param location The location which is being searched.
     * @param animals  The list of discoverable animals.
     */
    public void startGame(String location, List<String> animals) {
        board.resetBoard();
        board.setLocation(location);
        for (String animal : animals) {
            board.addAnimal(locations.get(location).getAnimal(animal));
        }
        board.generateCandidates();
        updateBoardDisplay();
    }

    /**
     * Returns the animal object with the provided name, if it is discoverable in the current location.
     *
     * @param name The name of the animal to return.
     * @return The animal object which has the provided name.
     */
    public List<String> getAnimalsFromLocation(String name) {
        Location location = locations.get(name);
        List<Animal> animals = location.getAnimals();
        return animals.stream().map(Animal::getName).collect(Collectors.toList());
    }

    /**
     * Confirms that the provided animal was hit in the provided block.
     *
     * @param block  The block in which the animal was found.
     * @param animal The animal which was found.
     */
    public void confirmHit(Block block, String animal) {
        board.confirmHit(block, animal);
    }

    /**
     * Confirms that the provided block is empty.
     *
     * @param block The block which is empty.
     */
    public void confirmMiss(Block block) {
        board.confirmMiss(block);
    }

    /**
     * @return A list of all locations.
     */
    public List<String> getLocationList() {
        return new ArrayList<>(locations.keySet());
    }

    /**
     * @return The height of the window.
     */
    public double getHeight() {
        return height;
    }
}