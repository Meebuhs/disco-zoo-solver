package discozoosolver;

/**
 * An immutable class representing an Animal.
 * <p>
 * Animals are the focus of disco zoo's gameplay. Each game is a 5x5 board populated with 1-3 animals from the current
 * location. Each animal is unique and is identified by its name. They also have a pattern which defines how
 * their tiles are laid out on the game board.
 */
public class Animal {
    private final String name;
    private final Pattern pattern;

    /**
     * Sole constructor for Animal which sets its name and pattern as provided.
     *
     * @param name    Name of the animal
     * @param pattern Pattern of the animal
     */
    public Animal(String name, Pattern pattern) {
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
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return name;
    }
}
