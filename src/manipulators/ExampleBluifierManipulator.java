package manipulators;

import java.awt.*;
import java.util.Random;

public class ExampleBluifierManipulator extends Manipulator {

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        return new Color(0, 0, brightness);
    }
}
