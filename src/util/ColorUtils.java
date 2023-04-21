package util;

import java.awt.*;

/**
 * Helper class with some methods that might be useful.
 *
 * If you make a helper for your manipulator, think
 * about if other people may find it useful and slap
 * it here instead!
 *
 * @author Maxx Batterton
 */
public class ColorUtils {

    public static int getLuminance(Color c) {
        return (int) (0.2126d*c.getRed() + 0.7152d*c.getGreen() + 0.0722d*c.getBlue());
    }

    public static float getBrightness(Color c) {
        return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[2];
    }

    public static Color setBrightness(Color c, float f) {
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], f));
    }
}
