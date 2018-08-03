package main.ui;

import javafx.scene.layout.VBox;

public class OptionMenu {
    private VBox display;

    public OptionMenu() {
        display = new VBox();
        display.setPrefWidth(270);
    }

    public VBox getDisplay() {
        return display;
    }
}
