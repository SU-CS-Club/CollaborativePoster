package manipulators;

import util.ColorUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Manipulator {

    protected BufferedImage image;

    public abstract Color getColorAtPoint(int x, int y, float brightness, Random random);

    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        image = inputImage;
        BufferedImage modified = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                Color sourceColor = new Color(inputImage.getRGB(x, y));
                modified.setRGB(x, y, getColorAtPoint(
                        x, y, ColorUtils.getBrightness(sourceColor), random).getRGB()
                );
            }
        }
        return modified;
    }
}
