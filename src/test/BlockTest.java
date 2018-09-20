import discozoosolver.Block;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BlockTest {

    private static Block testBlock1;
    private static Block testBlock2;
    private static Block testBlock3;

    @BeforeAll
    static void createTestBlocks() {
        testBlock1 = new Block(1, 2);
        testBlock2 = new Block(1, 2);
        testBlock3 = new Block(-1, -2);
    }

    @Test
    void testGettersAndSetters() {
        assertAll(
                () -> assertEquals(testBlock1.x(), 1),
                () -> assertEquals(testBlock2.y(), 2),
                () -> assertEquals(testBlock1, testBlock2),
                () -> assertNotEquals(testBlock1, testBlock3)
        );
    }
}