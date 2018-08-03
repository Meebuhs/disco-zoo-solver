package main.discozoosolver;

import java.util.List;

public class Candidate {
    private Animal animal;
    private List<Block> position;

    public Candidate(Animal animal, List<Block> position) {
        this.animal = animal;
        this.position = position;
    }

    public List<Block> getPosition() {
        return position;
    }

    public Animal getAnimal() {
        return animal;
    }

    @Override
    public String toString() {
        return animal.getName() + ": " + position;
    }
}
