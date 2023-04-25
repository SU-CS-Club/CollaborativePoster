/**
 * Manipulator that takes Vintagecomputer.png and inverts the colors
 * 
 * @author Gabriel Morgan
 */
package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VintageComputerManipulator extends ImageManipulator {

    public VintageComputerManipulator() {
        super("images/VintageComputer.png");
    }

    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {

    	if(brightness == 0) return Color.BLACK; // Added by Dr. Schrum to see background
    	
    	// Get the original color of the pixel
        Color color = super.getColorAtPoint(x, y, brightness, random);
        // Invert each color channel value by subtracting it from 255 (the maximum color value)
        int r = 255 - color.getRed();
        int g = 255 - color.getGreen();
        int b = 255 - color.getBlue();
        // Create a new color with the inverted color channels
        return new Color(r, g, b);
    }
}
