import discozoosolver.Animal;
import discozoosolver.Block;
import discozoosolver.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalTest {

    static private Pattern pattern;
    static private Animal zebra;

    @BeforeAll
    static void createTestAnimal() {
        List<Block> zebraBlocks = new ArrayList<>();
        zebraBlocks.add(new Block(1, 0));
        zebraBlocks.add(new Block(0, 1));
        zebraBlocks.add(new Block(2, 1));
        zebraBlocks.add(new Block(1, 2));
        pattern = new Pattern(zebraBlocks);
        zebra = new Animal("Zebra", pattern);
    }

    @Test
    void testGettersAndSetters() {
        assertAll(
                () -> assertEquals(zebra.getName(), "Zebra", "Animal.getName returns the wrong name."),
                () -> assertEquals(zebra.getPattern(), pattern, "Animal.getPattern returns the wrong pattern")
        );
    }
}