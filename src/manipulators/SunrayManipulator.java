package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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
        Color backgroundColor = new Color(255, 255, 0);
        // Color adjustedColor = ColorUtils.setBrightness(sourceColor, newBrightness);
        Color sunrayColor = new Color (255, 170, 51);

        float blendFactor = sunrayFactor;
        float backgroundBlendFactor = 0.4f;

        int red = (int) (sourceColor.getRed() * (1 - blendFactor - backgroundBlendFactor) + sunrayColor.getRed() * blendFactor + backgroundColor.getRed() * backgroundBlendFactor);
        int green = (int) (sourceColor.getGreen() * (1 - blendFactor - backgroundBlendFactor) + sunrayColor.getGreen() * blendFactor + backgroundColor.getGreen() * backgroundBlendFactor);
        int blue = (int) (sourceColor.getBlue() * (1 - blendFactor - backgroundBlendFactor) + sunrayColor.getBlue() * blendFactor + backgroundColor.getBlue() * backgroundBlendFactor);
                
        return new Color(
            Math.max(0, Math.min(255, red)),
            Math.max(0, Math.min(255, green)),
            Math.max(0, Math.min(255, blue)
        ));

    }

    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }
}
