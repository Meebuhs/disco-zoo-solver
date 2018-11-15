import discozoosolver.Cell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.SolverApp;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {

    private static int x = 2;
    private static int y = 4;
    private static Cell cell;

    @BeforeAll
    static void createTestCell() {
        SolverApp solver = new SolverApp();
        cell = new Cell(x, y, solver);
    }

    @BeforeEach
    void resetCell() {
        cell.resetCell();
    }

    @Test
    void testCoordinateGetters() {
        assertAll(
                () -> assertEquals(cell.getX(), x, "Cell.getX returns the incorrect value"),
                () -> assertEquals(cell.getY(), y, "Cell.getY returns the incorrect value")
        );
    }

    @Test
    void testStatusFlags() {
        assertFalse(cell.getFinalised(), "Cell.finalised should be initialised as false");
        assertFalse(cell.getKnown(), "Cell.known should be initialised as false");
        assertFalse(cell.getPriority(), "Cell.priority should be initialised as false");

        cell.setFinalised(true);
        cell.setKnown(true);
        cell.setPriority(true);

        assertTrue(cell.getFinalised(), "Cell.setFinalised should update finalised flag");
        assertTrue(cell.getKnown(), "Cell.setFinalised should update known flag");
        assertTrue(cell.getPriority(), "Cell.setFinalised should update priority flag");
    }

    @Test
    void testCellReset() {
        assertAll(
                () -> assertEquals(cell.getCount(), 0, "Cell.resetCell should reset Cell.count to 0"),
                () -> assertFalse(cell.getFinalised(), "Cell.resetCell should reset Cell.finalised to false"),
                () -> assertFalse(cell.getKnown(), "Cell.resetCell should reset Cell.known to false"),
                () -> assertFalse(cell.getPriority(), "Cell.resetCell should reset Cell.priority to false"),
                () -> assertEquals(cell.getAnimals().size(), 0, "Cell.resetCell should cause Cell.animals to be empty")
        );
    }

    @Test
    void testCellCount() {
        assertEquals(cell.getCount(), 0, "Cell.count should be initialised at 0");

        cell.incrementCount();
        assertEquals(cell.getCount(), 1, "Cell.incrementCount should increase count by 1");

        cell.clearCount();
        assertEquals(cell.getCount(), 0, "Cell.clearCount should reset count to 0");
    }

    @Test
    void testAddingAndRemovingAnimals() {
        assertEquals(cell.getAnimals().size(), 0, "Cell.animals should initially be empty");

        cell.addAnimal("Penguin");
        List<String> animals = new ArrayList<>();
        animals.add("Penguin");
        assertEquals(cell.getAnimals().size(), 1, "Cell.addAnimal is not adding 1 animal");
        assertEquals(cell.getAnimals(), animals, "Cell.addAnimal is not adding animals correctly");

        cell.addAnimal("Walrus");
        animals.add("Walrus");
        assertEquals(cell.getAnimals().size(), 2, "Cell.addAnimal should add 1 animal");
        assertEquals(cell.getAnimals(), animals, "Cell.addAnimal is not adding animals correctly");

        cell.clearAnimals();
        assertEquals(cell.getAnimals().size(), 0, "Cell.clearAnimals should cause Cell.animals to be empty");
    }

    @Test
    void testCheckIfEmptyOnEmptyCell() {
        assertEquals(cell.getAnimals().size(), 0, "Cell.resetCell should cause Cell.animals to be empty");
        cell.checkIfEmpty();
        assertEquals(cell.getFinalised(), true, "Cell.checkIfEmpty should set an empty cell to finalised");
    }

    @Test
    void testCheckIfEmptyOnNonEmptyCell() {
        cell.addAnimal("Yeti");
        assertEquals(cell.getFinalised(), false, "Cell.resetCell should cause Cell.animals to be empty");
        assertEquals(cell.getAnimals().size(), 1, "Cell.addAnimal should add 1 animal");

        cell.checkIfEmpty();
        assertEquals(cell.getFinalised(), false, "Cell.checkIfEmpty should not change finalised for a non-empty cell");
    }
}