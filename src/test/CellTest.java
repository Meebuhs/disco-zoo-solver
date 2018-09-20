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
                () -> assertEquals(cell.getX(), x),
                () -> assertEquals(cell.getY(), y)
        );
    }

    @Test
    void testStatusFlags() {
        assertFalse(cell.getFinalised());
        assertFalse(cell.getKnown());
        assertFalse(cell.getPriority());

        cell.setFinalised(true);
        cell.setKnown(true);
        cell.setPriority(true);

        assertTrue(cell.getFinalised());
        assertTrue(cell.getKnown());
        assertTrue(cell.getPriority());
    }

    @Test
    void testCellReset() {
        assertAll(
                () -> assertEquals(cell.getCount(), 0),
                () -> assertFalse(cell.getFinalised()),
                () -> assertFalse(cell.getKnown()),
                () -> assertFalse(cell.getPriority()),
                () -> assertEquals(cell.getAnimals().size(), 0)
        );
    }

    @Test
    void testCellCount() {
        assertEquals(cell.getCount(), 0);

        cell.incrementCount();
        assertEquals(cell.getCount(), 1);

        cell.clearCount();
        assertEquals(cell.getCount(), 0);
    }

    @Test
    void testAnimals() {
        assertEquals(cell.getAnimals().size(), 0);

        cell.addAnimal("Penguin");
        List<String> animals = new ArrayList<>();
        animals.add("Penguin");
        assertEquals(cell.getAnimals().size(), 1);
        assertEquals(cell.getAnimals(), animals);

        assertEquals(cell.getFinalised(), false);
        cell.checkIfEmpty();
        assertEquals(cell.getFinalised(), false);

        cell.clearAnimals();
        assertEquals(cell.getAnimals().size(), 0);
        cell.checkIfEmpty();
        assertEquals(cell.getFinalised(), true);
    }


}