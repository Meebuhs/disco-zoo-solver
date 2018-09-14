package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

class ToolPane {
    private SolverApp solver;
    private ToolBar toolBar;
    private List<ComboBox<String>> dropdowns;

    ToolPane(SolverApp solver) {
        this.solver = solver;
        this.dropdowns = new ArrayList<>();
        this.toolBar = new ToolBar(createAnimalSelection(), new Separator());
        this.toolBar.setPrefHeight(40);
        initiateDropdowns();
    }

    private HBox createAnimalSelection() {
        createDropdowns(4);

        Button startGame = new Button();
        startGame.setText("Start Game");
        startGame.setOnAction((ActionEvent event) -> startGame());

        HBox animalSelection = new HBox();
        animalSelection.getChildren().addAll(dropdowns);
        animalSelection.getChildren().add(startGame);
        animalSelection.setSpacing(10);
        animalSelection.setAlignment(Pos.CENTER_LEFT);

        return animalSelection;
    }

    private void initiateDropdowns() {
        ObservableList<String> locationOptions = FXCollections.observableArrayList(solver.getLocationList());

        ComboBox<String> locationDropdown = dropdowns.get(0);
        locationDropdown.setItems(locationOptions);
        locationDropdown.getSelectionModel().selectFirst();

        initialiseAnimalOptions(locationDropdown.getSelectionModel().getSelectedItem());
    }

    private void initialiseAnimalOptions(String location) {
        for (int i = 1; i < dropdowns.size(); i++) {
            ComboBox<String> animalDropdown = dropdowns.get(i);
            if (i == 1) {
                animalDropdown.setItems(FXCollections.observableArrayList(
                        solver.getAnimalsFromLocation(location)));
            } else {
                ObservableList<String> animalOptionsWithNone = FXCollections.observableArrayList(
                        solver.getAnimalsFromLocation(location));
                animalOptionsWithNone.add(0, "None");
                animalDropdown.setItems(animalOptionsWithNone);
            }
        }
    }

    private void createDropdowns(int number) {
        for (int i = 0; i < number; i++) {
            dropdowns.add(createDropdown(100));
        }

        setDropdownOnActions();
    }

    private ComboBox<String> createDropdown(int width) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(width);
        return comboBox;
    }

    private void setDropdownOnActions() {
        ComboBox<String> locationDropdown = dropdowns.get(0);
        locationDropdown.setOnAction((ActionEvent event) ->
                initialiseAnimalOptions(locationDropdown.getSelectionModel().getSelectedItem()));
        for (int i = 1; i < dropdowns.size(); i++) {
            ComboBox<String> animalDropdown = dropdowns.get(i);
            animalDropdown.valueProperty().addListener((obs, oldAnimal, newAnimal) -> updateAvailableAnimalOptions
                    (dropdowns.indexOf(animalDropdown), oldAnimal, newAnimal));
        }
    }

    private void updateAvailableAnimalOptions(int dropdownIndex, String oldAnimal, String newAnimal) {
        for (int i = 1; i < dropdowns.size(); i++) {
            ComboBox<String> animalDropdown = dropdowns.get(i);
            if (!(i == dropdownIndex)) {
                if (!(newAnimal == null || newAnimal.equals("None"))) {
                    animalDropdown.getItems().remove(newAnimal);
                }
                if (!(oldAnimal == null || oldAnimal.equals("None"))) {
                    animalDropdown.getItems().add(getAnimalIndex(oldAnimal, i), oldAnimal);
                }
            }
        }
    }

    private int getAnimalIndex(String oldAnimal, int dropdownIndex) {
        ComboBox<String> dropdown = dropdowns.get(dropdownIndex);
        List<String> animals = solver.getAnimalsFromLocation(dropdowns.get(0).getSelectionModel().getSelectedItem());

        int index = 0;
        if (dropdownIndex > 1) {
            if (oldAnimal.equals("None")) {
                return 0;
            } else {
                index++;
            }
        }

        for (int i = index; i < dropdown.getItems().size(); i++) {
            if (animals.indexOf(oldAnimal) > animals.indexOf(dropdown.getItems().get(i))) {
                index++;
            }
        }

        return index;
    }

    private void startGame() {
        List<String> animals = new ArrayList<>();
        ComboBox<String> locationDropdown = dropdowns.get(0);
        String location = locationDropdown.getValue();
        for (int i = 1; i <= 3; i++) {
            ComboBox<String> animalDropdown = dropdowns.get(i);
            String selected = animalDropdown.getSelectionModel().getSelectedItem();
            if (!(selected == null || selected.equals("None"))) {
                animals.add(selected);
            }
        }
        if (!(animals.isEmpty())) {
            solver.startGame(location, animals);
        }
    }

    ToolBar getToolBar() {
        return toolBar;
    }
}
