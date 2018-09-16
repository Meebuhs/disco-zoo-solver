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

class ToolPane {
    private SolverApp solver;
    private ToolBar toolBar;
    private ComboBox<String> locationDropdown;
    private List<ToggleButton> animalButtons;
    private int numberSelected;
    private List<String> selectedAnimals;
    private Button startButton;

    ToolPane(SolverApp solver) {
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

    private ComboBox<String> createLocationDropdown() {
        ObservableList<String> locationOptions = FXCollections.observableArrayList(solver.getLocationList());

        ComboBox<String> locationDropdown = new ComboBox<>();

        locationDropdown.setPrefHeight(55);
        locationDropdown.setPrefWidth(150);

        locationDropdown.setItems(locationOptions);
        locationDropdown.getSelectionModel().selectFirst();

        locationDropdown.setOnAction((ActionEvent event) ->
                updateAnimalOptions(locationDropdown.getSelectionModel().getSelectedItem()));

        return locationDropdown;
    }

    private Button createStartButton() {
        Button startButton = new Button();
        startButton.setPrefHeight(55);
        startButton.setText("Start Game");
        startButton.setDisable(true);
        startButton.setOnAction((ActionEvent event) -> startGame());
        return startButton;
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
            setButtonStates(true);
        } else {
            numberSelected--;
            selectedAnimals.remove(clickedAnimal);
            setButtonStates(false);
        }
    }

    private void setButtonStates(boolean animalAdded) {
        if (animalAdded) {
            if (numberSelected == 3) {
                for (ToggleButton button : animalButtons) {
                    if (!(button.isSelected())) {
                        button.setDisable(true);
                    }
                }
            } else if (numberSelected == 1) {
                startButton.setDisable(false);
            }
        } else {
            if (numberSelected == 2) {
                for (ToggleButton button : animalButtons) {
                    if (button.isDisable()) {
                        button.setDisable(false);
                    }
                }
            } else if (numberSelected == 0) {
                startButton.setDisable(true);
            }
        }
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
