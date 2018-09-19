import discozoosolver.Animal;
import discozoosolver.Block;
import discozoosolver.Location;
import discozoosolver.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    static private Location location;
    static private List<Animal> animals;
    static private Animal seal;

    @BeforeAll
    static void createTestLocation() {
        Block block = new Block(2, 2);
        List<Block> blocks = new ArrayList<>();
        blocks.add(block);
        animals = new ArrayList<>();
        animals.add(new Animal("Penguin", new Pattern(blocks)));
        seal = new Animal("Seal", new Pattern(blocks));
        animals.add(seal);
        location = new Location("Polar", animals);
    }

    @Test
    void testGettersAndSetters() {
        assertAll(
                () -> assertEquals(location.getName(), "Polar"),
                () -> assertEquals(location.getAnimals(), animals)
        );
    }

    @Test
    void testGetAnimal() {
        assertThrows(NoSuchElementException.class, () -> location.getAnimal("Kangaroo"), "Kangaroo does not exist in Polar");
        assertEquals(location.getAnimal("Seal"), seal);
    }
}