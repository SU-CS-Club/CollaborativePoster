package manipulators;

import java.awt.*;
import java.util.Random;

/**
 * Manipulator that generates a matrix themed background behind the logo
 *
 * @author Mark Mueller
 */
public class MatrixManipulator extends Manipulator {

    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        // places white for the logo
        if (brightness==0) {
            return new Color(255,255,255);
        }
        // Two arrays, to store lines that go longer or shorter
        int[] goodArr = {0,5,9,14,16,21,25,30};
        int[] badArr = {2,7,11,13,16,19,23,31};

        // Generates the green streaks
        int rand = random.nextInt(20);
        if(x%32>=0&&x%32<=8){
            // If closer to top of image, more likley to place green
            int tempy=10-(y/100);

            // Loops through two arrays, if part of either row, gives a 
            // greater or worse chance for a green pixel to be placed
            for(int i=0;i<goodArr.length;i++){
                int temp = x;
                if(temp-goodArr[i]*32>=0&&temp-goodArr[i]*32<=8){
                    rand+=6;
                }else if (temp-badArr[i]*32>0&&temp-badArr[i]*32<8){
                    rand-=5;
                }
            }
            // Accounts for spot in height of the picture
            rand+=tempy-5;
            if(rand>16) // If meets threshold, places green
                return new Color(0, 255, 0);
        }
        // Otherwise, black is placed
        return new Color(0, 0, 0);
    }
}
