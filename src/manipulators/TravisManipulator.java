package manipulators;
import java.awt.Color;
import java.util.Random;
/**
 * @author Travis Rafferty
 * returns a yellow black or white cell based on the mod of the x and y
 */
public class TravisManipulator extends Manipulator {
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        double bright = (random.nextFloat() % .99);
        int value = (x % 3 + y % 3) % 3;
        //mod of x + y cordinate
        if (value == 0) {
            return new Color(1.0f, 1.0f, brightness);
        }
        //returns yellow if mod = 0
        else if (value == 1) {
            return Color.black;
        }
        //returns black if mod = 1
        else {
            return Color.white;
        }
        //returns white if mod doesn't = 1 or 0
    }
}
