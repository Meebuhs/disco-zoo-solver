package ui;

import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;

/**
 * Simple text line toolbar which is used to display relevant messages to the user.
 */
public class Console {
    private ToolBar display;
    private Text consoleText;

    /**
     * Sole constructor for a console. The text is initialised to an empty string and its position is set.
     */
    public Console() {
        consoleText = new Text("");
        display = new ToolBar(consoleText);
        display.getStyleClass().add("bottom");
        display.setPrefHeight(40);
    }

    /**
     * @return The display element for this console.
     */
    ToolBar getDisplay() {
        return display;
    }

    /**
     * @param text The text string to display in the console.
     */
    void setText(String text) {
        consoleText.setText(text);
    }
}
