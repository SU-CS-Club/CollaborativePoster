package manipulators;

import java.awt.*;
import java.util.Random;

/**
 * Example Manipulator that fills the screen with a simple cyan to red gradient
 *
 * @author Maxx Batterton
 */
public class ExampleGradientManipulator extends Manipulator {

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        float red = ((float) x) / image.getWidth();
        float other = Math.max(0, brightness-red); // Clamps it to minimum of zero as to not exceed 0.0-1.0 range
        return new Color(red*brightness, other, other);
    }
}
