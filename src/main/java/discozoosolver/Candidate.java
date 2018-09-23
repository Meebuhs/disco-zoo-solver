package discozoosolver;

import java.util.List;

/**
 * An immutable class representing a potential candidate. A candidate is uniquely defined by an animal and its positions.
 * <p>
 * This class does not contain any logic for checking that the positions for a candidate comply with the pattern for an
 * animal. It is expected that this is done when the candidate is created.
 * <p>
 * For example take a Cockatoo at position (2, 1). The pattern for a cockatoo is (0, 0), (1, 1) and (1, 2). The
 * candidate then occupies cells (2, 1), (3, 2) and (3, 3).
 */
public class Candidate {
    private final Animal animal;
    private final List<Block> position;

    /**
     * Sole constructor for Candidate which sets the animal and its position as provided.
     *
     * @param animal   The animal for which this is a candidate.
     * @param position The list of cell positions occupied by this candidate.
     */
    public Candidate(Animal animal, List<Block> position) {
        this.animal = animal;
        this.position = position;
    }

    /**
     * @return the animal for which this is a candidate.
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * @return The list of cell positions occupied by this candidate.
     */
    public List<Block> getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return animal.getName() + ": " + position;
    }
}
