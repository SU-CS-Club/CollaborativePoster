package manipulators;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.*;

/**
 * Abstract manipulator that can recreate a scaled version of any input file. The class
 * is abstract because it requires a child class to call this constructor (as super) with
 * a specific image file path provided.
 * 
 * @author Dr. Jacob Schrum
 *
 */
public abstract class ImageManipulator extends Manipulator {

	protected BufferedImage sourceImage; // Image that you want to recreate

	/**
	 * Given path to a specific image, load and save that file as a BuffredImage.
	 * 
	 * @param filePath Path to image. Should be a local path for portability.
	 */
	public ImageManipulator(String filePath) {
		try {
			sourceImage = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public Color getColorAtPoint(int x, int y, float brightness, Random random) {
		int scaledX = (int) ((((float) x) / image.getWidth()) * sourceImage.getWidth());
		int scaledY = (int) ((((float) y) / image.getHeight()) * sourceImage.getHeight());
		int rgb = sourceImage.getRGB(scaledX, scaledY);
		return new Color(rgb);
	}

}
