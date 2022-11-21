package manipulators;

import util.ColorUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Manipulator {

    // instance of the image being processed, saved for easy access in getColorAtPoint()
    protected BufferedImage image;

    /**
     * Returns the color a coordinate should be rendered as.
     *
     * @param x The x (horizontal) coordinate of the pixel
     * @param y The y (vertical) coordinate of the pixel
     * @param brightness The brightness of the source pixel in the range 0.0 to 1.0
     * @param random A random instance to be used for randomization
     * @return Generated color of a pixel for the manipulated image
     */
    public abstract Color getColorAtPoint(int x, int y, float brightness, Random random);

    /**
     * Gets the color at every point of the input image.
     * For more complex manipulators, you may want to
     * override this method.
     *
     * @param inputImage The input image to be processed
     * @param random A random instance to be used for randomization
     * @return A fully or partially manipulated image
     */
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        // Update local copy of the input image
        image = inputImage;
        // Create copy of image to set pixels of
        BufferedImage modified = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Loop for every pixel, calling getColorAtPoint for each position
        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                // Get color at source image position
                Color sourceColor = new Color(inputImage.getRGB(x, y));
                // Set modified image's pixel to the output of this Manipulator's getColorAtPoint method
                modified.setRGB(x, y, getColorAtPoint(
                        x, y, ColorUtils.getBrightness(sourceColor), random).getRGB()
                );
            }
        }
        return modified;
    }
}
