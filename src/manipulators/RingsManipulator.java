package manipulators;

import java.awt.*;
import java.util.Random;

public class RingsManipulator extends Manipulator{
    int MAXCOLOR = (int) (Integer.MAX_VALUE*Math.random());

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        int colorNum = MAXCOLOR/(x*y+1);

        if (brightness > 0.50) {
            return new Color(colorNum);
        }
        return new Color((int) (brightness * random.nextInt(255)));
        //return Color.black;
    }

}
