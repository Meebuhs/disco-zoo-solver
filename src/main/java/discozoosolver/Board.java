package discozoosolver;

import ui.BoardDisplay;
import ui.SolverApp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class representing the game board.
 * <p>
 * The board keeps a list of Cells and maintains the set of possible animal candidates based on the current state of
 * the cells and the solver.
 */
public class Board {
    private SolverApp solver;
    private List<Candidate> candidates;
    private List<Animal> animals;
    private List<Cell> cells;
    private BoardDisplay boardDisplay;
    private String location;

    /**
     * Sole constructor for Board which sets the associated solver as provided. Its cells are initialised as a
     * BOARD_SIZE x BOARD_SIZE grid of empty cells.
     *
     * @param solver The solver which controls game state.
     */
    public Board(SolverApp solver) {
        this.solver = solver;
        candidates = new ArrayList<>();
        animals = new ArrayList<>();
        this.cells = createCells();
        this.boardDisplay = new BoardDisplay(this);
    }

    /**
     * @return a list of BOARD_SIZE x BOARD_SIZE empty cells.
     */
    private List<Cell> createCells() {
        List<Cell> newCells = new ArrayList<>();
        for (int y = 0; y < Constants.BOARD_SIZE; y++) {
            for (int x = 0; x < Constants.BOARD_SIZE; x++) {
                Cell cell = new Cell(x, y, solver);
                newCells.add(cell);
            }
        }
        return newCells;
    }

    /**
     * Processes a move input by the user where the given animal is confirmed to be in the given block. Candidates are
     * eliminated if the candidate is a different animal and it contains the given block or if it is the same animal
     * and does not contain the given block.
     *
     * @param block  The block which contains the animal.
     * @param animal The animal which was discovered.
     */
    public void confirmHit(Block block, String animal) {
        Predicate<Candidate> candidatePredicate = c -> (c.getPosition().contains(block) ^ c.getAnimal().getName()
                .equals(animal));
        candidates.removeIf(candidatePredicate);
        setFinalised(block);
        processCells();

    }

    /**
     * Processes a move input by the user where it is confirmed that the given block does not contain an animal.
     * Candidates are eliminated if they contain the given block.
     *
     * @param block The block which is known to be empty.
     */
    public void confirmMiss(Block block) {
        Predicate<Candidate> candidatePredicate = c -> c.getPosition().contains(block);
        candidates.removeIf(candidatePredicate);
        setFinalised(block);
        processCells();
    }

    /**
     * Sets the cell associated with the given block as finalised. This occurs when the user has confirmed the contents
     * of a cell via confirmHit or confirmMiss.
     *
     * @param block The block to set as finalised.
     */
    private void setFinalised(Block block) {
        Cell cell = getCell(block.x(), block.y());
        cell.setFinalised(true);
        /* The board may have confirmed the cell's contents as known which takes rendering precedence over finalised. */
        cell.setKnown(false);
    }

    /**
     * Generates a complete list of candidates from the current set of animals.
     */
    public void generateCandidates() {
        // Each pattern is offset from the top left block in its bounding box. Thus for each cell, if the patterns
        // bounds fit on the board, the candidate is possible.
        for (Animal animal : animals) {
            int height = animal.getPattern().getHeight();
            int width = animal.getPattern().getWidth();
            for (int y = 0; y <= Constants.BOARD_SIZE - height; y++) {
                for (int x = 0; x <= Constants.BOARD_SIZE - width; x++) {
                    List<Block> blocks = new ArrayList<>();
                    for (Block block : animal.getPattern().getBlocks()) {
                        blocks.add(new Block(x + block.x(), y + block.y()));
                    }
                    candidates.add(new Candidate(animal, blocks));
                }
            }
        }
        processCells();
    }

    /**
     * Updates the contents of each cell based on the current candidates and checks to see if any cells are now known.
     */
    private void processCells() {
        generateCellContents();
        checkForKnownCells();
        for (Cell cell : this.cells) {
            cell.checkIfEmpty();
        }
    }

    /**
     * Sets the count of possible candidates and the list of animals which could be in each cell.
     */
    private void generateCellContents() {
        clearCounts();
        clearAnimalsInCells();
        for (Candidate candidate : candidates) {
            for (Block block : candidate.getPosition()) {
                String animal = candidate.getAnimal().getName();
                Cell cell = getCell(block.x(), block.y());
                cell.addAnimal(animal);
                cell.incrementCount();
            }
        }
    }

    /**
     * Clears the counts for each cell. The count is simply a measure of how many candidates include a given cell.
     */
    private void clearCounts() {
        for (Cell cell : cells) {
            cell.clearCount();
        }
    }

    /**
     * Clears the possible animals for each cell.
     */
    private void clearAnimalsInCells() {
        for (Cell cell : cells) {
            cell.clearAnimals();
        }
    }

    /**
     * Checks the current board state and marks any known cells as such.
     * <p>
     * A cell can be marked as known if:
     * 1) It is part of a candidate which is the only candidate for an animal. In this case all cells in the
     * candidate are known to be that animal as each animal must appear.
     * 2) It is contained by all candidates for an animal.
     * <p>
     * If any cells are set to be known for any animal, the process is repeated as cells marked as known for animal 3
     * may remove candidates for animal 1 and as such another check must be made.
     */
    private void checkForKnownCells() {
        boolean changesMade = true;
        while (changesMade) {
            changesMade = false;

            for (Animal animal : animals) {
                String name = animal.getName();
                List<Candidate> options = getCandidatesForAnimal(name);

                if (options.size() == 1) {
                    // Case 1 - Set candidate to be known
                    changesMade = setKnownCandidate(options.get(0));
                } else {
                    // Check case 2
                    changesMade = checkForKnownBlock(options);
                }
            }
        }

        generateCellContents();
        updatePriorities();
    }

    /**
     * Returns a list of the current candidates for a given animal.
     *
     * @param animal The animal for which to retrieve possible candidates.
     * @return The list of potential candidates.
     */
    private List<Candidate> getCandidatesForAnimal(String animal) {
        List<Candidate> options = new ArrayList<>();

        for (Candidate candidate : candidates) {
            if (candidate.getAnimal().getName().equals(animal)) {
                options.add(candidate);
            }
        }

        return options;
    }

    /**
     * Checks if there is a block which is common to every candidate and sets it to known.
     *
     * @param options The list of potential candidates for an animal.
     * @return true if any changes are made.
     */
    private boolean checkForKnownBlock(List<Candidate> options) {
        boolean changesMade = false;
        Candidate firstCandidate = options.get(0);

        // Compare each block in the first candidate against each other candidate. If a block is contained
        // by every candidate, it must be in the first so we only need to check these.
        for (Block block : firstCandidate.getPosition()) {
            boolean known = true;
            for (Candidate candidate : options) {
                if (!(candidate.getPosition().contains(block))) {
                    known = false;
                    break;
                }
            }
            if (known) {
                // Case 2 - Set the block to be known
                changesMade = setKnownBlock(block, firstCandidate.getAnimal().getName());
            }
        }
        return changesMade;
    }

    /**
     * Sets known status for each block in the candidate.
     *
     * @param candidate The candidate which is known.
     * @return true if a change is made to any block in the candidate.
     */
    private boolean setKnownCandidate(Candidate candidate) {
        boolean changesMade = false;
        String animal = candidate.getAnimal().getName();
        for (Block block : candidate.getPosition()) {
            if (setKnownBlock(block, animal)) {
                changesMade = true;
            }
        }
        return changesMade;
    }

    /**
     * Sets the status of the block which is known to contain animal.
     *
     * @param block  The block which is known to contain animal.
     * @param animal The animal which block contains.
     * @return true if a change is made to a block.
     */
    private boolean setKnownBlock(Block block, String animal) {
        Cell cell = getCell(block.x(), block.y());
        if (!(cell.getFinalised() || cell.getKnown())) {
            // Remove any candidate using the block
            Predicate<Candidate> candidatePredicate = c -> (c.getPosition().contains(block) ^ c.getAnimal().getName()
                    .equals(animal));
            candidates.removeIf(candidatePredicate);
            cell.setKnown(true);
            cell.setFinalised(false);
            return true;
        }
        return false;
    }

    /**
     * Calculates the cells which are most likely to contain an animal and sets them as the suggested cells.
     */
    private void updatePriorities() {
        int maxCount = 0;
        for (Cell cell : cells) {
            if (!(cell.getFinalised() || cell.getKnown())) {
                if (cell.getCount() < maxCount) {
                    continue;
                } else if (cell.getCount() > maxCount) {
                    maxCount = cell.getCount();
                    clearCellPriorities();
                }
                cell.setPriority(true);
            }
        }
    }

    /**
     * Clears the priority status for each cell.
     */
    private void clearCellPriorities() {
        for (Cell cell : cells) {
            cell.setPriority(false);
        }
    }

    /**
     * Resets the board returning it to an empty state.
     */
    public void resetBoard() {
        clearCells();
        clearCandidates();
        clearAnimals();
    }


    /**
     * Clears each cell returning them to an empty state.
     */
    private void clearCells() {
        for (Cell cell : cells) {
            cell.resetCell();
        }
    }

    /**
     * @return The list of candidates.
     */
    public List<Candidate> getCandidates() {
        return candidates;
    }

    /**
     * Clears the list of current candidates.
     */
    private void clearCandidates() {
        candidates = new ArrayList<>();
    }

    /**
     * Clears the list of animals.
     */
    private void clearAnimals() {
        animals = new ArrayList<>();
    }

    /**
     * Update the display linked to this board.
     */
    public void updateDisplay() {
        boardDisplay.updateDisplay();
    }

    /**
     * Add an animal to the current list of animals.
     *
     * @param animal the animal to add.
     */
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    /**
     * @return the current game location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the list of cells.
     */
    public List<Cell> getCells() {
        return cells;
    }

    /**
     * @return the cell located at (x, y)
     */
    private Cell getCell(int x, int y) {
        return cells.get(y * Constants.BOARD_SIZE + x);
    }

    /**
     * @return the display class linked to this board.
     */
    public BoardDisplay getBoardDisplay() {
        return boardDisplay;
    }
}