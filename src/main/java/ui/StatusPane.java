package ui;

import javafx.scene.layout.VBox;

public class StatusPane {
    private VBox display;

    public StatusPane() {
        display = new VBox();
        display.setPrefWidth(270);
    }

    VBox getDisplay() {
        return display;
    }
}
