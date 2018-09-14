package discozoosolver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Location {
    private String name;
    private List<Animal> animals;

    public Location(String name) {
        this(name, new ArrayList<>());
    }

    Location(String name, List<Animal> animals) {
        this.name = name;
        this.animals = animals;
    }

    public String getName() {
        return name;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public Animal getAnimal(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        throw new NoSuchElementException(name + " does not exist in " + name);
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

    @Override
    public String toString() {
        return name + ":\n" + animals.toString();
    }
}