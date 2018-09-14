package ui;

import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;

public class Console {
    private ToolBar display;
    private Text consoleText;

    public Console() {
        consoleText = new Text("");
        display = new ToolBar(consoleText);
        display.getStyleClass().add("bottom");
        display.setPrefHeight(40);
    }

    ToolBar getDisplay() {
        return display;
    }

    void setText(String text) {
        consoleText.setText(text);
    }
}
