package util;

import java.awt.*;

public class ColorUtils {

    public static int getLuminance(Color c) {
        return (int) (0.2126d*c.getRed() + 0.7152d*c.getGreen() + 0.0722d*c.getBlue());
    }

    public static float getBrightness(Color c) {
        return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[2];
    }
}
