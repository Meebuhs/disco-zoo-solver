package main.discozoosolver;

import main.ui.CellDisplay;
import main.ui.SolverApp;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Cell {
    private Set<String> animals;
    private int x;
    private int y;
    private int count;
    private Boolean known;
    private Boolean finalised;
    private Boolean priority;
    private CellDisplay cellDisplay;
    private SolverApp solver;

    public Cell(int x, int y, SolverApp solver) {
        animals = new LinkedHashSet<>();
        this.x = x;
        this.y = y;
        count = 0;
        known = false;
        finalised = false;
        priority = false;
        cellDisplay = new CellDisplay(solver, this);
        this.solver = solver;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        this.count = 0;
    }

    public void incrementCount() {
        count++;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Boolean getKnown() {
        return known;
    }

    public void setKnown(Boolean known) {
        this.known = known;
    }

    public Boolean getFinalised() {
        return finalised;
    }

    public void setFinalised(Boolean finalised) {
        this.finalised = finalised;
    }

    public void addAnimal(String animal) {
        animals.add(animal);
    }

    public void clearAnimals() {
        animals = new LinkedHashSet<>();
    }

    public List<String> getAnimals() {
        return new ArrayList<>(animals);
    }

    public CellDisplay getDisplay() {
        return cellDisplay;
    }

    public void updateDisplay() {
        checkIfEmpty();
        cellDisplay.populateCell();
    }

    public void resetCell() {
        animals = new LinkedHashSet<>();
        count = 0;
        known = false;
        finalised = false;
        priority = false;
        cellDisplay.clearContents();
    }

    private void checkIfEmpty() {
        if (animals.size() == 0) {
            this.finalised = true;
        }
    }

    public void confirmHit(String animal) {
        System.out.println(animal + " hit at " + x + ", " + y);
        solver.confirmHit(new Block(x, y), animal);
    }

    public void confirmMiss() {
        System.out.println("Miss at " + x + ", " + y);
        solver.confirmMiss(new Block(x, y));
    }
}
