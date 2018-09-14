package discozoosolver;

/** Simple class which represents a block at coordinates (x, y) */
public class Block {
    private int x;
    private int y;

    /** Creates a new block. */
    Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x coordinate of the block.
     */
    int x() {
        return x;
    }

    /**
     * @return the y coordinate of the block.
     */
    int y() {
        return y;
    }

    /**
     * @return the string representation of the block.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Two blocks are equal if the x and y coordinates are equal.
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
}
