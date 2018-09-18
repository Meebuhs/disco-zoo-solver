package discozoosolver;

import java.util.List;

public class Pattern {
    private List<Block> blocks;
    private int height;
    private int width;

    public Pattern(List<Block> blocks) {
        this.blocks = blocks;
        height = 1;
        width = 1;
        setDimensions();
    }

    List<Block> getBlocks() {
        return blocks;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    int getSize() {
        return blocks.size();
    }

    private void setDimensions() {
        for (Block block : blocks) {
            if (block.x() >= width) {
                width = block.x() + 1;
            }
            if (block.y() >= height) {
                height = block.y() + 1;
            }
        }
    }

    @Override
    public String toString() {
        return("main.discozoosolver.Pattern: " + blocks.toString());
    }
}
