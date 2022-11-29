package manipulators;

import java.awt.*;
import java.util.Random;

/**
 * Example Manipulator that fills whitespace with yellow, cyan, or pink randomly
 *
 * @author Maxx Batterton
 */
public class ExampleRandomManipulator extends Manipulator {
    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        int option = random.nextInt(3);
        if (option == 0) {
            return new Color(brightness, brightness, 0);
        } else if (option == 1) {
            return new Color(0, brightness, brightness);
        } else { // option == 2
            return new Color(brightness, 0, brightness);
        }
    }
}
