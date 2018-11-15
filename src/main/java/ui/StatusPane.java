package ui;

import javafx.scene.layout.VBox;

/**
 * Display class for the status pane.
 */
public class StatusPane {
    private VBox display;

    /**
     * Sole constructor for StatusPane.
     */
    public StatusPane() {
        display = new VBox();
        display.setPrefWidth(270);
    }

    /**
     * @return The display element for this status pane.
     */
    VBox getDisplay() {
        return display;
    }
}
