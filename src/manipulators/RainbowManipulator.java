/**
 * Manipulator that makes all the whitespace/pixles that have a brigtness of over 0.5, into a rainbow design.
 *
 * @author Aidan Balakrishnan
 *
 */
package manipulators;

import java.awt.*;
import java.util.Random;

public class RainbowManipulator extends Manipulator{
    int MAXCOLOR = (int) (Integer.MAX_VALUE*Math.random());


    public Color getColorAtPoint(int x, int y, float brightness, Random random) {

        if (brightness > 0.50) {
            return new Color(Math.min(x*2,255), Math.max(image.getWidth() - x*2, 0), Math.min(y*2,255));
        }
        return new Color((int) (brightness * random.nextInt(255)));
        //return Color.black;
    }

}
