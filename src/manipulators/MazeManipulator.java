package manipulators;

import java.awt.*;
import java.util.Random;

/**
 * Covers the tile in a maze-like pattern
 *
 * @author Maxx Batterton
 */
public class MazeManipulator extends BigPixelManipulator {

    private final Color GAP = Color.WHITE;
    private final Color WALL = Color.ORANGE;

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        if (x % 2 == 0 && y % 2 == 0) {
            return WALL;
        } else if (!(x % 2 == 1 && y % 2 == 1) && random.nextInt(3) == 0) {
            return WALL;
        }
        return GAP;
    }

}
