package manipulators;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import util.ColorUtils;

/**
 * Manipulator that inverts the radial gradient
 * 
 * @author Kyle Keleher
 * 
 */

 public class InvertedRadialGradientManipulator extends Manipulator {
    
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        // int centerX = image.getWidth() / 2;
        // int centerY = image.getHeight() / 2;
        // double maxDistance = Math.sqrt(centerX * centerX + centerY * centerY);
        // double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        // float invertedBrightness = 1.0f - (float) (distance / maxDistance);

        // invertedBrightness = Math.max(0, Math.min(1, invertedBrightness));
        // float newBrightness = (float) (brightness * invertedBrightness);

        // float angle = (float) Math.atan2(y - centerY, x - centerX);

        // float swirlStrength = 6.0f;
        // angle += swirlStrength * (float) distance / maxDistance;

        // float hue = (angle + (float) Math.PI) / (2 * (float) Math.PI);

        // float red = Math.min(1.0f, Math.max(0.0f, (float) x / image.getWidth() * hue)); 
        // float green = Math.min(1.0f, Math.max(0.0f, (float) y / image.getWidth() * hue)); 
        // float blue = Math.min(1.0f, Math.max(0.0f, 0.6f * hue));
        
        // red *= (x % 5 == 0) ? 0.7f : 1.0f;
        // green *= (x % 7 == 0) ? 0.5f : 1.0f;
        // blue *= (Math.abs(x - y) % 4 == 0) ? 0.8f : 1.0f;

        
        return Color.BLACK;



        // double distance = Point2D.distance(x, y, centerX, centerY);
        // double maxDistance = Math.hypot(centerX, centerY);


        // float red = (float) 0.1;
        // float green = (float) 0.3;
        // float blue = (float) 0.2;
        // return new Color(red, green, blue, 1.0f);
    }

    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }
 }

