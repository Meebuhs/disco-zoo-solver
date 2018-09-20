package discozoosolver;

import ui.CellDisplay;
import ui.SolverApp;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A class representing a cell on the board. Each cell has x and y coordinates, a count of the number of potential
 * candidates, a set of animals which could be found and a group of boolean flags.
 * <p>
 * Flags:
 * - Finalised: the user has confirmed its contents.
 * - Known: the board has determined the cells contents.
 * - Priority: the board has determined it as the best cell to choose.
 * If a cell has been finalised, it cannot be known or a priority. If it is known, it cannot be a priority.
 * <p>
 * The cell also has references to the display class linked to this cell, as well as the solver which created it.
 */
public class Cell {
    private Set<String> animals;
    private final int x;
    private final int y;
    private int count;
    private Boolean finalised;
    private Boolean known;
    private Boolean priority;
    private CellDisplay cellDisplay;
    private SolverApp solver;

    /**
     * Creates a new cell.
     *
     * @param x      The x coordinate of the cell.
     * @param y      The y coordinate of the cell.
     * @param solver The solver instance which created this cell.
     */
    public Cell(int x, int y, SolverApp solver) {
        animals = new LinkedHashSet<>();
        this.x = x;
        this.y = y;
        count = 0;
        finalised = false;
        known = false;
        priority = false;
        cellDisplay = new CellDisplay(solver, this);
        this.solver = solver;
    }

    /**
     * Confirms a hit for this cell in the solver for the given animal.
     *
     * @param animal The animal which was hit.
     */
    public void confirmHit(String animal) {
        System.out.println(animal + " hit at " + x + ", " + y);
        solver.confirmHit(new Block(x, y), animal);
    }

    /**
     * Confirms a miss for this cell in the solver.
     */
    public void confirmMiss() {
        System.out.println("Miss at " + x + ", " + y);
        solver.confirmMiss(new Block(x, y));
    }

    /**
     * @return The set of animals as a list.
     */
    public List<String> getAnimals() {
        return new ArrayList<>(animals);
    }

    /**
     * @param animal The animal to add to this cell.
     */
    public void addAnimal(String animal) {
        animals.add(animal);
    }

    /**
     * Clear the set of animals which can be found in this cell.
     */
    public void clearAnimals() {
        animals = new LinkedHashSet<>();
    }

    /**
     * @return The x coordinate of the cell.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y coordinate of the cell.
     */
    public int getY() {
        return y;
    }

    /**
     * @return The number of potential candidates for the cell.
     */
    public int getCount() {
        return count;
    }

    /**
     * Increments the potential candidate count for the cell.
     */
    public void incrementCount() {
        count++;
    }

    /**
     * Clears the potential candidate count for the cell.
     */
    public void clearCount() {
        this.count = 0;
    }

    /**
     * @return The finalised status of the cell.
     */
    public Boolean getFinalised() {
        return finalised;
    }

    /**
     * @param finalised The finalised status of the cell.
     */
    public void setFinalised(Boolean finalised) {
        this.finalised = finalised;
    }

    /**
     * Sets the cell to finalised if the set of discoverable animals is empty.
     */
    public void checkIfEmpty() {
        if (animals.size() == 0) {
            this.finalised = true;
        }
    }

    /**
     * @return The known status of the cell.
     */
    public Boolean getKnown() {
        return known;
    }

    /**
     * @param known The known status of the cell.
     */
    public void setKnown(Boolean known) {
        this.known = known;
    }

    /**
     * @return The priority status of the cell.
     */
    public Boolean getPriority() {
        return priority;
    }

    /**
     * @param priority The priority status of the cell.
     */
    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    /**
     * @return The display class associated to this cell.
     */
    public CellDisplay getDisplay() {
        return cellDisplay;
    }

    /**
     * Updates the cell display.
     */
    public void updateDisplay() {
        cellDisplay.populateCell();
    }

    /**
     * Resets the cell to its initial state.
     */
    public void resetCell() {
        animals = new LinkedHashSet<>();
        count = 0;
        known = false;
        finalised = false;
        priority = false;
        cellDisplay.clearContents();
    }
}
