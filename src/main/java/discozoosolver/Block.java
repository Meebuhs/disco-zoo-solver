package discozoosolver;

/**
 * Simple immutable pair-like class which represents a block at coordinates (x, y).
 */
public class Block {
    private final int x;
    private final int y;

    /** Creates a new block. */
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x coordinate of the block.
     */
    public int x() {
        return x;
    }

    /**
     * @return the y coordinate of the block.
     */
    public int y() {
        return y;
    }

    /**
     * Returns whether two blocks are equal. They are considered equal if their x and y coordinates are equal.
     *
     * @param other The block to compare to.
     * @return true if the two blocks are equivalent.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Block)) {
            return false;
        }
        Block b = (Block) other;
        return x == b.x() && y == b.y();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
