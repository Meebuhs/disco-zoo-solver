package discozoosolver;

import java.util.List;

/**
 * An immutable class which represents the pattern of an animal. A pattern contains a list of blocks which define the
 * cell positions occupied by an animal stored as offsets from an origin set at the top left block of a bounding box.
 */
public class Pattern {
    private final List<Block> blocks;
    private int height;
    private int width;

    /**
     * Creates a new pattern.
     *
     * @param blocks The list of blocks which make up the pattern.
     */
    public Pattern(List<Block> blocks) {
        this.blocks = blocks;
        setDimensions();
    }

    /**
     * Sets the width and the height of the pattern. Patterns are stored as offsets from their origin at (0, 0) and as
     * such the width is the max(block.x) + 1 for all blocks.
     */
    private void setDimensions() {
        width = 0;
        height = 0;
        for (Block block : blocks) {
            if (block.x() >= width) {
                width = block.x() + 1;
            }
            if (block.y() >= height) {
                height = block.y() + 1;
            }
        }
    }

    /**
     * @return The list of blocks which make up this pattern.
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * @return The width of this pattern.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height of this pattern.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The number of cells occupied by this pattern.
     */
    public int getSize() {
        return blocks.size();
    }

    @Override
    public String toString() {
        return ("Pattern: " + blocks.toString());
    }
}
