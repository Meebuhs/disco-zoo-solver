package main.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.discozoosolver.Animal;
import main.discozoosolver.Block;
import main.discozoosolver.Board;
import main.discozoosolver.Constants;
import main.discozoosolver.GameDataParser;
import main.discozoosolver.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolverApp extends Application {
    private Console console;
    private Map<String, Location> locations;
    private Board board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        locations = GameDataParser.parseData();
        board = new Board(this);

        primaryStage.setTitle("Disco Zoo Solver");

        BorderPane border = new BorderPane();
        ToolPane toolPane = new ToolPane(this);
        StatusPane statusPane = new StatusPane();
        OptionMenu optionMenu = new OptionMenu();
        BoardDisplay boardDisplay = board.getBoardDisplay();
        BorderPane.setMargin(boardDisplay.getDisplay(), new Insets(95, 45, 95, 45));
        console = new Console();

        border.setTop(toolPane.getToolBar());
        border.setLeft(statusPane.getDisplay());
        border.setCenter(boardDisplay.getDisplay());
        border.setRight(optionMenu.getDisplay());
        border.setBottom(console.getDisplay());

        primaryStage.setScene(new Scene(border, 1080, 720));
        primaryStage.show();
    }

    public void setConsole(String message) {
        console.setText(message);
    }

    public void updateBoardDisplay() {
        board.updateDisplay();
    }

    public void startGame(String location, List<String> animals) {
        board.resetBoard();
        board.setLocation(location);
        for (String animal : animals) {
            board.addAnimal(locations.get(location).getAnimal(animal));
        }
        board.generateCandidates();
        board.printPositions();
        updateBoardDisplay();
    }

    public List<String> getAnimalsFromLocation(String name) {
        Location location = locations.get(name);
        List<Animal> animals = location.getAnimals();
        return animals.stream().map(Animal::getName).collect(Collectors.toList());
    }

    private void addAnimal(String location, String name) {
        Animal animal = locations.get(location).getAnimal(name);
        board.addAnimal(animal);
    }

    public void confirmHit(Block block, String animal) {
        board.confirmHit(block, animal);
    }

    public void confirmMiss(Block block) {
        board.confirmMiss(block);
    }

    public List<String> getLocationList() {
        return new ArrayList<>(locations.keySet());
    }
}