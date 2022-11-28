package manipulators;

import java.io.File;

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
}
