package discozoosolver;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * An immutable class representing an in game location. Each location is identified by its name and has a list of
 * possible animals associated with it.
 */
public class Location {
    private final String name;
    private final List<Animal> animals;

    /**
     * Sole constructor for Location which sets the name and animals as provided.
     *
     * @param name    The name of the location.
     * @param animals The list of animals which can be encountered in this location.
     */
    public Location(String name, List<Animal> animals) {
        this.name = name;
        this.animals = animals;
    }

    /**
     * @return The name of this location.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The list of animals which can be encountered in this location.
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Returns the Animal object with the given name if it can be found in this location.
     *
     * @param name The name of the animal to return.
     * @return The Animal object with the given name if it can be found in this location.
     */
    public Animal getAnimal(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        throw new NoSuchElementException(name + " does not exist in " + this.name);
    }

    @Override
    public String toString() {
        return name + ":\n" + animals.toString();
    }
}