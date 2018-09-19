import discozoosolver.Block;
import discozoosolver.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PatternTest {

    static private List<Block> blocks;
    static private Pattern pattern;

    @BeforeAll
    static void createTestAnimal() {
        blocks = new ArrayList<>();
        blocks.add(new Block(0, 1));
        blocks.add(new Block(1, 1));
        blocks.add(new Block(2, 0));
        blocks.add(new Block(2, 2));
        pattern = new Pattern(blocks);
    }

    @Test
    void testGettersAndSetters() {
        assertAll(
                () -> assertEquals(pattern.getBlocks(), blocks),
                () -> assertEquals(pattern.getHeight(), 3),
                () -> assertEquals(pattern.getWidth(), 3),
                () -> assertEquals(pattern.getSize(), 4)
        );
    }
}