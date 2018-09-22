package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Display class for the toolpane which handles the location and animal selection.
 */
public class ToolPane {
    private SolverApp solver;
    private ToolBar toolBar;
    private ComboBox<String> locationDropdown;
    private List<ToggleButton> animalButtons;
    private int numberSelected;
    private List<String> selectedAnimals;
    private Button startButton;

    /**
     * Sole constructor for the toolpane. Initialises the location dropdown, animal toggles and start game button.
     *
     * @param solver The solver connected to this toolpane. Used to trigger the game start
     */
    public ToolPane(SolverApp solver) {
        this.solver = solver;
        this.locationDropdown = createLocationDropdown();
        this.startButton = createStartButton();
        this.animalButtons = new ArrayList<>();
        this.selectedAnimals = new ArrayList<>();
        this.numberSelected = 0;
        initialiseAnimalButtons(locationDropdown.getSelectionModel().getSelectedItem());
        this.toolBar = new ToolBar(createAnimalSelection(), new Separator());
        this.toolBar.setPrefHeight(70);
    }

    /**
     * Creates the location dropdown. This dropdown is a combobox populated with the location list from the solver.
     * Choosing a location from the dropdown triggers an update for the animal selection buttons.
     *
     * @return The location dropdown ComboBox element.
     */
    private ComboBox<String> createLocationDropdown() {
        ComboBox<String> dropdown = new ComboBox<>();

        ObservableList<String> locationOptions = FXCollections.observableArrayList(solver.getLocationList());
        dropdown.setItems(locationOptions);
        dropdown.getSelectionModel().selectFirst();

        dropdown.setPrefHeight(55);
        dropdown.setPrefWidth(150);

        dropdown.setOnAction((ActionEvent event) ->
                updateAnimalOptions(dropdown.getSelectionModel().getSelectedItem()));

        return dropdown;
    }

    /**
     * @return The start button which triggers the start of a new game.
     */
    private Button createStartButton() {
        Button button = new Button();
        button.setPrefHeight(55);
        button.setText("Start Game");
        button.setDisable(true);
        button.setOnAction((ActionEvent event) -> startGame());
        return button;
    }

    private void initialiseAnimalButtons(String location) {
        for (int i = 0; i < 6; i++) {
            animalButtons.add(createToggleButton());
        }
        updateAnimalOptions(location);
    }

    private ToggleButton createToggleButton() {
        ToggleButton button = new ToggleButton();
        button.setOnAction(this::handleAnimalSelect);
        return button;
    }

    private void updateAnimalOptions(String location) {
        List<String> animals = solver.getAnimalsFromLocation(location);
        for (int i = 0; i < animalButtons.size(); i++) {
            ToggleButton button = animalButtons.get(i);
            String path = String.format("animals/%s.png", animals.get(i));
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Image image = new Image(classLoader.getResourceAsStream(path));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(45);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
            button.setGraphic(imageView);
            button.setPrefSize(55, 55);
        }
        resetSelectedButtons();
        setStartState();
    }

    private void resetSelectedButtons() {
        for (ToggleButton button : animalButtons) {
            button.setSelected(false);
            button.setDisable(false);
        }
        numberSelected = 0;
        selectedAnimals.clear();
    }

    private HBox createAnimalSelection() {
        HBox animalSelection = new HBox();
        animalSelection.getChildren().add(locationDropdown);
        animalSelection.getChildren().addAll(animalButtons);
        animalSelection.getChildren().add(startButton);
        animalSelection.setSpacing(20);
        animalSelection.setAlignment(Pos.CENTER_LEFT);

        return animalSelection;
    }

    private void handleAnimalSelect(ActionEvent event) {
        ToggleButton clickedButton = (ToggleButton) event.getSource();
        int clickedIndex = animalButtons.indexOf(clickedButton);
        String clickedAnimal = solver.getAnimalsFromLocation(
                locationDropdown.getSelectionModel().getSelectedItem()).get(clickedIndex);
        if (clickedButton.isSelected()) {
            numberSelected++;
            selectedAnimals.add(clickedAnimal);
        } else {
            numberSelected--;
            selectedAnimals.remove(clickedAnimal);
        }
        setStartState();
    }

    private void setStartState() {
        startButton.setDisable(!(numberSelected > 0 && numberSelected <= 3));
    }

    private void startGame() {
        if (!(selectedAnimals.isEmpty())) {
            solver.startGame(locationDropdown.getSelectionModel().getSelectedItem(), selectedAnimals);
        }
    }

    ToolBar getToolBar() {
        return toolBar;
    }
}
