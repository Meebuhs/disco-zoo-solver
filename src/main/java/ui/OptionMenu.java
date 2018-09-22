package ui;

import javafx.scene.layout.VBox;

/**
 * Display class for the options menu.
 */
public class OptionMenu {
    private VBox display;

    /**
     * Sole constructor for the options menu
     */
    public OptionMenu() {
        display = new VBox();
        display.setPrefWidth(270);
    }

    /**
     * @return The display element for this options menu.
     */
    VBox getDisplay() {
        return display;
    }
}
