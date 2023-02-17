package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NyanCatManipulator extends ImageManipulator{


    public NyanCatManipulator() {
        super("images/Nyan_cat.png");
    }

    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        if (brightness < 0.25) {
            return new Color(0xFFFFFF);
        }

return super.getColorAtPoint(x,y,brightness,random);


    }

}
