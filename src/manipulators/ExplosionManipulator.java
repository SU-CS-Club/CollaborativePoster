/**
 * Manipulator that creates an explosion that dissolves some of the logo
 * 
 * @author Marcus Leese
 */

package manipulators;
import java.awt.Color;
import java.util.Random;

public class ExplosionManipulator extends ImageManipulator {
    public ExplosionManipulator() {
        super("images/explosion.png");
    }
    @Override
    public Color getColorAtPoint(int x, int y, float brightness, Random random) {
        int option = random.nextInt(3);
        if (option > 1 && brightness < 0.25){ // Blow up 2/3 of the logo
            return Color.WHITE;
        } else { 
            return super.getColorAtPoint(x,y,brightness,random);
        }
    }
}