import discozoosolver.Animal;
import discozoosolver.Block;
import discozoosolver.Board;
import discozoosolver.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.SolverApp;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private static Board board;

    @BeforeAll
    static void createBoard() {
        SolverApp solver = new SolverApp();
        board = new Board(solver);
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void testLocation() {
        assertNull(board.getLocation(), "Location shouldn't be set upon creation");
        board.setLocation("City");
        assertEquals(board.getLocation(), "City", "Board.setLocation should update location");
    }

    @Test
    void testNumberOfCells() {
        assertEquals(board.getCells().size(), 25, "The size of the board should be 5x5");
    }

    @Test
    void testSingleCandidate() {
        assertEquals(board.getCandidates().size(), 0, "The board should be initialised with 0 candidates");

        List<Block> position = new ArrayList<>();
        position.add(new Block(0, 0));
        position.add(new Block(1, 0));
        position.add(new Block(2, 0));
        position.add(new Block(3, 0));
        Animal sheep = new Animal("Sheep", new Pattern(position));
        board.addAnimal(sheep);

        board.generateCandidates();
        assertEquals(board.getCandidates().size(), 10, "10 candidates should be generated");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0 || i == 4) {
                    assertFalse(board.getCells().get(j * 5 + i).getPriority(), "Cells in the center three columns should be prioritised");
                } else {
                    assertTrue(board.getCells().get(j * 5 + i).getPriority(), "Cells in the center three columns should be prioritised");
                }
            }
        }

        board.confirmMiss(new Block(2, 0));
        assertEquals(board.getCandidates().size(), 8, "A miss should eliminate two candidates");
        for (int i = 0; i < 5; i++) {
            assertTrue(board.getCells().get(i).getFinalised(), "Cell should be finalised by the confirmed miss");
            assertFalse(board.getCells().get(i).getKnown(), "Cell's which are finalised should not be known");
            assertFalse(board.getCells().get(i).getPriority(), "Cell's which are finalised should not be prioritised");
        }

        board.confirmHit(new Block(1, 1), "Sheep");
        assertAll(
                () -> assertEquals(board.getCandidates().size(), 2, "Two candidates should remain after confirming this hit"),
                () -> assertTrue(board.getCells().get(6).getFinalised(), "Cell should be finalised after being confirmed"),
                () -> assertTrue(board.getCells().get(7).getKnown(), "Both candidates contain this cell so it should be known"),
                () -> assertTrue(board.getCells().get(8).getKnown(), "Both candidates contain this cell so it should be known")
        );

        board.confirmHit(new Block(0, 1), "Sheep");
        assertEquals(board.getCandidates().size(), 1, "Only one candidate should remain");
        assertTrue(board.getCells().get(5).getFinalised(), "Cell should be finalised after being confirmed");
    }

    @Test
    void testMultipleCandidates() {
        assertEquals(board.getCandidates().size(), 0, "The board should be initialised with 0 candidates");

        setUpMultipleCandidateTest();
        assertAll(
                () -> assertTrue(board.getCells().get(4).getFinalised(), "Cell is empty and should be marked as finalised"),
                () -> assertTrue(board.getCells().get(12).getPriority(), "Cell should be marked a priority")
        );

        board.confirmHit(new Block(2, 2), "Eagle");
        assertAll(
                () -> assertTrue(board.getCells().get(12).getFinalised(), "Cell should be finalised after being confirmed"),
                () -> assertFalse(board.getCells().get(12).getKnown(), "Cell should be not be known after being confirmed"),
                () -> assertFalse(board.getCells().get(12).getPriority(), "Cell should not be a priority after being confirmed"),
                () -> assertTrue(board.getCells().get(7).getPriority(), "Cell should be marked a priority"),
                () -> assertTrue(board.getCells().get(17).getPriority(), "Cell should be marked a priority"),
                () -> assertTrue(board.getCells().get(19).getFinalised(), "Cell is empty and should be marked as finalised")
        );

        board.confirmHit(new Block(2, 3), "Goat");
        assertAll(
                () -> assertTrue(board.getCells().get(17).getFinalised(), "Cell should be marked finalised after being confirmed"),
                () -> assertTrue(board.getCells().get(5).getFinalised(), "Cell is empty and should be marked finalised"),
                () -> assertTrue(board.getCells().get(20).getFinalised(), "Cell is empty and should be marked finalised"),
                () -> assertTrue(board.getCells().get(21).getFinalised(), "Cell is empty and should be marked finalised"),
                () -> assertTrue(board.getCells().get(7).getPriority(), "Cell should be marked a priority")
        );

        board.confirmHit(new Block(2, 1), "Coyote");
        assertAll(
                () -> assertTrue(board.getCells().get(7).getFinalised(), "Cell should be marked finalised after being confirmed"),
                () -> assertTrue(board.getCells().get(1).getKnown(), "Only one candidate remains for Eagle, cell should be known"),
                () -> assertTrue(board.getCells().get(6).getKnown(), "Only one candidate remains for Eagle, cell should be known"),
                () -> assertTrue(board.getCells().get(8).getKnown(), "Only one candidate remains for Coyote, cell should be known"),
                () -> assertTrue(board.getCells().get(14).getKnown(), "Only one candidate remains for Coyote, cell should be known")
        );
    }

    private void setUpMultipleCandidateTest() {
        List<Block> position = new ArrayList<>();
        position.add(new Block(0, 0));
        position.add(new Block(0, 1));
        position.add(new Block(1, 1));
        position.add(new Block(2, 1));
        Animal animal = new Animal("Goat", new Pattern(position));
        board.addAnimal(animal);


        position = new ArrayList<>();
        position.add(new Block(0, 0));
        position.add(new Block(0, 1));
        position.add(new Block(1, 2));
        animal = new Animal("Eagle", new Pattern(position));
        board.addAnimal(animal);


        position = new ArrayList<>();
        position.add(new Block(0, 0));
        position.add(new Block(1, 0));
        position.add(new Block(2, 1));
        animal = new Animal("Coyote", new Pattern(position));
        board.addAnimal(animal);
        board.generateCandidates();
    }
}
