package manipulators;

import util.ColorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Fills the tile with randomly colored big pixels
 *
 * @author Maxx Batterton
 */
public class BigPixelManipulator extends Manipulator {

    protected final int PIXEL_SIZE = 8;

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        return new Color(random.nextFloat(),random.nextFloat(),random.nextFloat());
    }

    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        // Update local copy of the input image
        image = inputImage;
        // Create copy of image to set pixels of
        BufferedImage modified = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Loop for every pixel, calling getColorAtPoint for each position
        for (int x = 0; x < inputImage.getWidth()/PIXEL_SIZE; x++) {
            for (int y = 0; y < inputImage.getHeight()/PIXEL_SIZE; y++) {
                // Get color at source image position
                // Set modified image's pixel to the output of this Manipulator's getColorAtPoint method
                Color c = getColorAtPoint(x, y, 0, random);
                for (int x2 = 0; x2 < PIXEL_SIZE; x2++) {
                    for (int y2 = 0; y2 < PIXEL_SIZE; y2++) {
                        int newX = (x*PIXEL_SIZE)+x2;
                        int newY = (y*PIXEL_SIZE)+y2;
                        Color sourceColor = new Color(inputImage.getRGB(newX, newY));

                        modified.setRGB(newX, newY, ColorUtils.setBrightness(c, ColorUtils.getBrightness(sourceColor)).getRGB());
                    }
                }

            }
        }
        return modified;
    }
}
