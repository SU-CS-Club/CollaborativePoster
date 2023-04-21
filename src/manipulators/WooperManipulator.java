package manipulators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Puts wooper in :)
 * He messes up the seal
 *
 * @author Mark Mueller
 */
public class WooperManipulator extends ImageManipulator {

    public WooperManipulator(){
        super("images/wooper.png");
    }
    
    @Override
    public BufferedImage transformImage(BufferedImage inputImage, Random random) {
        return super.transformImage(inputImage, random);
    }

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        
        if(super.getColorAtPoint(x,y,brightness,random).equals(new Color(41, 255, 57))){
            float seal = Math.max(0, brightness); // makes sure seal still visible
            return new Color(seal,seal,seal);
        }else if (brightness!=0) {
            return super.getColorAtPoint(x,y,brightness,random); // Otherwise prints Wooper
        }
        return new Color(255,255,255);
        
    }
}