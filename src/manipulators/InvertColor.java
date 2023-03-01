/**
 * Manipulator that inverts the underlying picture.
 *
 * @author Aidan Balakrishnan
 *
 */

package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class InvertColor extends Manipulator{
    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        float oppositeBrightness = 1-brightness;

        Color c = new Color(image.getRGB(x,y));

        int[] RGB = new int[3];

        RGB[0] = c.getRed();
        RGB[1] = c.getGreen();
        RGB[2] = c.getBlue();

return new Color(255-RGB[0],255-RGB[1],255-RGB[2]);
        }

    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }
}
