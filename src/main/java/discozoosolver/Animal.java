package discozoosolver;

/**
 * <p>
 * An immutable class representing an Animal.
 * </p>
 *
 * <p>
 * Animals are the focus of disco zoo's gameplay. Each game is a 5x5 board populated with 1-3 animals from the current
 * location. Each animal is unique and is identified by its name. They also have a pattern which defines how
 * their tiles are laid out on the game board.
 * </p>
 */
public class Animal {
    private String name;
    private Pattern pattern;

    /**
     * Creates a new animal.
     *
     * @param name Name of the animal
     * @param pattern Pattern of the animal
     */
    Animal(String name, Pattern pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    /**
     * @return the name of the animal.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the pattern of the animal.
     */
    Pattern getPattern() {
        return pattern;
    }

    /**
     * Calls the pattern's getSize method.
     * @return the number of tiles occupied by the animal.
     */
    public int getSize() {
        return pattern.getSize();
    }

    /**
     * @return the string representation of the animal.
     */
    @Override
    public String toString() {
        return name;
    }
}
