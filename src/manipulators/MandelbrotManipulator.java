package manipulators;

import java.awt.Color;
import java.util.Random;

/**
 * Manipulator that fills the screen with a blue gradient mandelbrot
 * set with random white pixels in the background.
 *
 * Reference: https://rosettacode.org/wiki/Mandelbrot_set
 *
 * @author Jackson Gill
 */
public class MandelbrotManipulator extends Manipulator {

    private static final int MAX_ITERATIONS = 200;
    // Color for pixels inside the Mandelbrot set
    private static final Color OUTSIDE_COLOR = Color.BLACK;
    // The range of blues to use for points outside the Mandelbrot set
    private static final Color BLUE_START_COLOR = new Color(0x0000FF);
    private static final Color BLUE_END_COLOR = new Color(0x00FFFF);
    // The probability of background dots
    private final double FILAMENT_PROBABILITY = 0.05;


    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        // Calculate the complex coordinates for the current pixel
        double x0 = ((x - image.getWidth() / 2.0) * 2.4 / image.getWidth()) - 0.5;
        double y0 = (y - image.getHeight() / 2.0) * 2.4 / image.getWidth();

        // Calculate the Mandelbrot set value for the current pixel
        double cx = 0;
        double cy = 0;
        int iterations = 0;
        while (cx * cx + cy * cy < 4.0 && iterations < MAX_ITERATIONS) {
            double xTemp = cx * cx - cy * cy + x0;
            cy = 2.0 * cx * cy + y0;
            cx = xTemp;
            iterations++;
        }

        // Fill pixel with color
        float start = 1 - (((float) x) / image.getWidth());
        // Pixel is inside the mandelbrot set, use gradient color
        if (iterations == MAX_ITERATIONS) {
            return interpolateColors(BLUE_START_COLOR, BLUE_END_COLOR, start);
        } else {
            // Create a color based on the Mandelbrot set value and the brightness of the pixel
            if (random.nextDouble() < FILAMENT_PROBABILITY) return Color.WHITE;
            return OUTSIDE_COLOR;
        }
    }

    /**
     * Interpolates between two colors by a given amount.
     *
     * @param color1 The first color
     * @param color2 The second color
     * @param t The interpolation amount (between 0 and 1)
     * @return The interpolated color
     */
    private Color interpolateColors(Color color1, Color color2, float t) {
        int red = (int) (color1.getRed() * (1 - t) + color2.getRed() * t);
        int green = (int) (color1.getGreen() * (1 - t) + color2.getGreen() * t);
        int blue = (int) (color1.getBlue() * (1 - t) + color2.getBlue() * t);
        return new Color(red, green, blue);
    }
}
