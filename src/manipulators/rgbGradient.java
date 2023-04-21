package manipulators;

import java.awt.*;
import java.util.Random;

/**
 * Creates a RGB gradient
 *
 * @author Matthew Volkin
 */
public class rgbGradient extends Manipulator {

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        float red = ((float)y )/image.getHeight(); 
        float green  = ((float)x)/image.getWidth();
        float blue =((float)image.getWidth()-x)/image.getWidth() ;
        return new Color(red*brightness, green*brightness, blue*brightness); 
    }
}
