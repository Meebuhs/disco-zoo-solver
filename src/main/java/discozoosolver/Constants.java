package discozoosolver;

import javafx.scene.paint.Color;

import java.util.Map;

public final class Constants {
    /* Size of the game board */
    static final int BOARD_SIZE = 5;
    /* Background colours for each environment type */
    protected static final Map<String, Color> BOARD_COLOURS = Map.ofEntries(
            Map.entry("Ice Age", Color.rgb(162, 187, 176)),
            Map.entry("Mountain", Color.rgb(119, 145, 133)),
            Map.entry("Moon", Color.rgb(116, 109, 106)),
            Map.entry("Mars", Color.rgb(201, 108, 90)),
            Map.entry("Jungle", Color.rgb(64, 151, 73)),
            Map.entry("Farm", Color.rgb(117, 183, 34)),
            Map.entry("Jurassic", Color.rgb(123, 69, 51)),
            Map.entry("Northern", Color.rgb(104, 151, 77)),
            Map.entry("Polar", Color.rgb(118, 182, 182)),
            Map.entry("Outback", Color.rgb(209, 142, 99)),
            Map.entry("City", Color.rgb(107, 176, 28)),
            Map.entry("Savanna", Color.rgb(216, 172, 69))
    );

    /* Dark blank icon filename */
    public static final String BLANK_DARK = "blank-dark";
    /* Light blank icon filename */
    public static final String BLANK_LIGHT = "blank-light";

    /* Proportion of the view height that a cell should occupy */
    public static final double CELL_HEIGHT_FACTOR = 0.125;

    /* Default window dimensions */
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1080;

    private Constants() {
        throw new AssertionError();
    }

}
