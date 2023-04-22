package manipulators;

import util.ColorUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Manipulator that creates a blue equalizer effect with a white logo.
 * 
 * @author Jace Salinas
 */

public class EqualizerManipulator extends Manipulator{
    private static final int BAR_COUNT = 8;
    private static final int BLUR_RADIUS = 3;

    private static float toFloat(int value) {
        return value / 255.0f;
    }
    private static float weightedAverage(float value1, float value2, float skew) {
        return ((1.0f-skew) * value1) + (skew * value2);
    }
    private static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }
    private static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }
    private static Color screen(Color color1, float brightness) {
        float[] rgb = color1.getRGBComponents(null);
        return new Color(1.0f-(1.0f-rgb[0])*(brightness), 1.0f-(1.0f-rgb[1])*(brightness), 1.0f-(1.0f-rgb[2])*(brightness));
    }

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        // Create a simple vertical gradient to each bar.
        float value = clamp(((float) y)/image.getHeight()*3.0f-1.0f, 0.0f, 1.0f)*0.8f;

        // Apply horizontal green-blue-purple gradient.
        float red = value * weightedAverage(toFloat(0x1F), toFloat(0xAF), (float)x/BAR_COUNT);
        float green = value * weightedAverage(toFloat(0xCF), toFloat(0x2F), (float)x/BAR_COUNT);
        float blue = value * weightedAverage(toFloat(0x9F), toFloat(0xDF), (float)x/BAR_COUNT);

        return new Color(red, green, blue);
    }

    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        // Update local copy of the input image
        image = inputImage;
        // Create copy of image to set pixels of
        BufferedImage modified = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Keep track of which bar is being drawn
        int prevBarNumber = -1;
        int barNumber = 0;
        // Keep track of a random height offset for each bar
        int heightOffset = 0;
        // Loop for every pixel, calling getColorAtPoint for each position
        for (int x = 0; x < inputImage.getWidth(); x++) {
            // Update bar index if necessary
            barNumber = clamp((int)(((float) x)/inputImage.getWidth()*BAR_COUNT), 0, BAR_COUNT-1);
            // If bar number changed, randomize new height offset
            if (barNumber > prevBarNumber) {
                prevBarNumber++;
                heightOffset = random.nextInt(-inputImage.getHeight()/4, inputImage.getHeight()/4);
            }
            for (int y = 0; y < inputImage.getHeight(); y++) {
                // Get color at source image position
                Color sourceColor = new Color(inputImage.getRGB(x, y));
                // Get source color's brightness
                float sourceBrightness = ColorUtils.getBrightness(sourceColor);
                // Get corresponding background color
                Color resultColor = getColorAtPoint(barNumber, y+heightOffset, sourceBrightness, random);
                // Draw horizontal black lines at specific intervals
                if (y % (inputImage.getHeight()/(BAR_COUNT*2)) == 0) resultColor = ColorUtils.setBrightness(resultColor, 0.0f);
                // Apply logo using screen filter
                resultColor = screen(resultColor, sourceBrightness);
                // Set modified image's pixel to the output of this Manipulator's getColorAtPoint method
                modified.setRGB(x, y, resultColor.getRGB());
            }
        }
        return modified;
    }
}
