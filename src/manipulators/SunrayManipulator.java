package manipulators;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import util.ColorUtils;

/**
 * Manipulator that adds a sunray effect
 * 
 * @author Kyle Keleher
 * 
 */

public class SunrayManipulator extends Manipulator {

    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;
        double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

        float angle = (float) Math.atan2(y - centerY, x - centerX);
        double normalizedDistance = distance / Math.max(image.getWidth(), image.getHeight());

        float sunrayFactor = (float) (Math.sin(angle * 10) * 0.5 + 0.5);
        float newBrightness = (float) (brightness * (1 - normalizedDistance - sunrayFactor));

        Color sourceColor = new Color (image.getRGB(x, y));
        Color adjustedColor = ColorUtils.setBrightness(sourceColor, newBrightness);
        Color sunrayColor = new Color (255, 235, 0);

        float blendFactor = sunrayFactor;

        int red = (int) (sourceColor.getRed() * (1 - blendFactor) + sunrayColor.getRed() * blendFactor);
        int green = (int) (sourceColor.getGreen() * (1 - blendFactor) + sunrayColor.getGreen() * blendFactor);
        int blue = (int) (sourceColor.getBlue() * (1 - blendFactor) + sunrayColor.getBlue() * blendFactor);
        
        return new Color(red, green, blue);
    }

    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }
}
