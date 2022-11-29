package manipulators;

import java.awt.*;
import java.util.Random;

/**
 * Example Manipulator that colors the image blue based on brightness
 *
 * @author Maxx Batterton
 */
public class ExampleBluifierManipulator extends Manipulator {

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        return new Color(0, 0, brightness);
    }
}
