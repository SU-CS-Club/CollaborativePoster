package manipulators;
import java.awt.*;
import java.util.Random;

/**
 * Creates an instance of a multicolored fractal web
 * inspired by: https://study.com/academy/lesson/how-to-create-a-fractal.html 
 *
 * @author Alejandro Medina
 */

public class FractalWebManipulator extends Manipulator {

    private static final int NUM_LINES = 7;
    private static final int MIN_RADIUS = 25;
    private static final int MAX_RADIUS = 800;

    /**
     * Returns the color a coordinate should be rendered as.
     *
     * @param x          The x (horizontal) coordinate of the pixel
     * @param y          The y (vertical) coordinate of the pixel
     * @param brightness The brightness of the source pixel in the range 0.0 to 1.0
     * @param random     A random instance to be used for randomization
     * @return Generated color of a pixel for the manipulated image
     */
    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        if (brightness != 0.0) {
            int width = image.getWidth();
            int height = image.getHeight();

            // Set up the graphics object
            Graphics g = image.createGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);

            // Calculate the center point
            int centerX = width / 2;
            int centerY = height / 2;

            // Set up the colors
            Color[] colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.PINK, Color.MAGENTA};

            // Draw the lines
            drawFractalWeb(g, centerX, centerY, MAX_RADIUS, 0, colors, 0, random);

            // Get the color at the specified point
            Color color = new Color(image.getRGB(x, y));

            return color;
        }
        return Color.WHITE;
    }

    private void drawFractalWeb(Graphics g, int centerX, int centerY, int radius, double angle, Color[] colors,
                                int depth, Random random) {
        // Calculate the end point of the line
        int x = centerX + (int) (radius * Math.cos(angle));
        int y = centerY + (int) (radius * Math.sin(angle));

        // Calculate the color index
        int colorIndex = random.nextInt(NUM_LINES);

        // Draw the line
        g.setColor(colors[colorIndex]);
        g.drawLine(centerX, centerY, x, y);

        // Recurse if necessary
        if (radius > MIN_RADIUS) {
            drawFractalWeb(g, centerX, centerY, radius / 2, angle + Math.PI / 4, colors, depth + 1, random);
            drawFractalWeb(g, centerX, centerY, radius / 2, angle - Math.PI / 4, colors, depth + 1, random);
            drawFractalWeb(g, x, y, radius / 2, angle + Math.PI / 4, colors, depth + 1, random);
            drawFractalWeb(g, x, y, radius / 2, angle - Math.PI / 4, colors, depth + 1, random);
        }
    }
}
