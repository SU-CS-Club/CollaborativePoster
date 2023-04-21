package manipulators;

/**
 * Creates rainbow zig-zag lines across the screen like an Easter Egg
 *
 * @author Anna Wicker
 */

import java.awt.*;
import java.util.Random;

public class ZigZagManipulator extends Manipulator {


    /**
     * Returns the color a coordinate should be rendered as.
     *
     * @param x          The x (horizontal) coordinate of the pixel
     * @param y          The y (vertical) coordinate of the pixel
     * @param brightness The brightness of the source pixel in the range 0.0 to 1.0
     * @param random     A random instance to be used for randomization
     * @return Generated color of a pixel for the manipulated image
     */
    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        if(brightness != 0.0) {
            int sum = (x % 6) + (y % 6);
            if ((sum % 6 == 0 || x % 6 == y % 6) && (y % 6 < 4)) {
                return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
            }
            else return Color.WHITE;
        }
        return Color.BLACK;
    }
}
