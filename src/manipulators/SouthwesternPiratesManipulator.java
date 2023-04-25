/**
 * Manipulator that changes the southwestern pirates athletic image and
 * changes any white color to a rainbow color as well as changes any grey color to an alternative green color that i like
 *
 * @author Matthew Swandal
 *
 */
package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SouthwesternPiratesManipulator extends ImageManipulator {

    public SouthwesternPiratesManipulator() {
        super("images/SU_pirates.png");
    }

    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
    	
    	if(brightness == 0) return Color.BLACK; // Added by Dr. Schrum to see background
    	
        Color color = super.getColorAtPoint(x, y, brightness, random);
        if (color.equals(Color.WHITE)) {
            float hue = random.nextFloat();
            return Color.getHSBColor(hue, 1f, 1f);
        } else if (isGrayColor(color)) { //changes gray color to the green color i selected
            return new Color(102, 255, 178); // alternative green color
        }
        return color;
    }
// checks if the color is gray in the picture
    private boolean isGrayColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return Math.abs(red - green) < 10 && Math.abs(red - blue) < 10 && Math.abs(green - blue) < 10;
    }
    
}    


