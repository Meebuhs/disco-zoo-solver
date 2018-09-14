package discozoosolver;

import java.util.List;

public class Candidate {
    private Animal animal;
    private List<Block> position;

    Candidate(Animal animal, List<Block> position) {
        this.animal = animal;
        this.position = position;
    }

    List<Block> getPosition() {
        return position;
    }

    Animal getAnimal() {
        return animal;
    }

    @Override
    public String toString() {
        return animal.getName() + ": " + position;
    }
}
