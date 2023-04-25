package manipulators;

import java.awt.Color;
import java.io.File;
import java.util.Random;

/**
 * Manipulator that creates the SU CS Club Logo.
 * 
 * @author Dr. Jacob Schrum
 *
 */
public class CSClubLogoManipulator extends ImageManipulator {
	public CSClubLogoManipulator() {
		super("images"+File.separator+"GreenLogo.png");
	}
	
	public Color getColorAtPoint(int x, int y, float brightness, Random random) {
		if(brightness == 0) return Color.WHITE; // Added to see background
		else return super.getColorAtPoint(x, y, brightness, random);
	}
}
