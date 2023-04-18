/**
 * Manipulator that puts marios with different colored outfits on top of the poster.
 *
 * @author Jubel Sanabria
 *
 */

package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MarioManipulator extends ImageManipulator{

    public MarioManipulator() {
        super("images/mario.png");
    }

    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        if(!super.getColorAtPoint(x, y, brightness, random).equals(Color.black)){ // Checks for a black pixel in the mario image
            if(super.getColorAtPoint(x, y, brightness, random).equals(new Color(247, 216, 165))){ // Checks for mario's main overalls color
                // If the white part of mario's overalls is found it will replace it with a rainbow color
                return new Color(Math.min(x*2,255), Math.min(Math.max(image.getWidth() - x*2, 0), 255), Math.min(y*2,255));
            }
            else {
                // If not it will set the original pixel of the mario image
                return super.getColorAtPoint(x,y,brightness,random);
            }
        }
        else {
            if (brightness < 0.25){
                return Color.black;
            }
            else {
                // Sets the background to a red sky gradient
                float red = ((float) y) / image.getHeight();
                float other = Math.max(0, brightness-red); // Clamps it to minimum of zero as to not exceed 0.0-1.0 range
                return new Color((float)1, (float) 0.7, other);
            }
        }
    }

}
