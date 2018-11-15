import discozoosolver.Animal;
import discozoosolver.Block;
import discozoosolver.Candidate;
import discozoosolver.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CandidateTest {

    static private Animal cockatoo;
    static private List<Block> position;
    static private Candidate candidate;

    @BeforeAll
    static void createTestCandidate() {
        List<Block> cockatooBlocks = new ArrayList<>();
        cockatooBlocks.add(new Block(0, 0));
        cockatooBlocks.add(new Block(1, 1));
        cockatooBlocks.add(new Block(1, 2));
        Pattern pattern = new Pattern(cockatooBlocks);
        cockatoo = new Animal("Cockatoo", pattern);

        position = new ArrayList<>();
        position.add(new Block(0, 0));
        position.add(new Block(1, 1));
        position.add(new Block(1, 2));
        candidate = new Candidate(cockatoo, position);
    }

    @Test
    void testGettersAndSetters() {
        assertAll(
                () -> assertEquals(candidate.getAnimal(), cockatoo, "Candidate.getAnimal returns incorrect value"),
                () -> assertEquals(candidate.getPosition(), position, "Candidate.getPosition returns incorrect value")
        );
    }

    @Test
    void testToString() {
        assertEquals(candidate.toString(), "Cockatoo: [(0, 0), (1, 1), (1, 2)]");
    }
}