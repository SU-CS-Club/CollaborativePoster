package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * Manipulator that creates a starburst effect by creating a burst of rays 
 * from a central point. 
 * 
 * @author Kyle Keleher
 * 
 */

 public class StarburstManipulator extends Manipulator {
    
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;
        float distance = (float) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        float angle = (float) Math.atan2(y - centerY, x - centerX);
        float maxDistance = (float) Math.sqrt(centerX * centerX + centerY * centerY);

        float normalizedDistance = distance / maxDistance;
        
        int rayCount = 50;
        int VariableRayCount = (int) (rayCount * (1 - normalizedDistance) + rayCount * 0.5 * normalizedDistance);
        float rayFactor = (float) (Math.sin(angle * VariableRayCount) * 0.5 + 0.5);

        float newBrightness = (float) (brightness * rayFactor);

        float hue = (float) (angle / Math.PI * 2);
        float saturation = 0.5f;

        return Color.getHSBColor(hue, saturation, newBrightness);
    }

    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }
 }

