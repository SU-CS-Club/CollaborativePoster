package manipulators;

import java.awt.Color;
import java.util.Random;

import picbreeder.ActivationFunctions;
import picbreeder.CartesianGeometricUtilities;
import picbreeder.EvolutionaryHistory;
import picbreeder.ILocated2D;
import picbreeder.TWEANN;
import picbreeder.TWEANNGenotype;
import picbreeder.Tuple2D;
import picbreeder.activationfunctions.FullLinearPiecewiseFunction;
import picbreeder.activationfunctions.HalfLinearPiecewiseFunction;

public class CPPNManipulator extends Manipulator {

	public static final int HUE_INDEX = 0;
	public static final int SATURATION_INDEX = 1;
	public static final int BRIGHTNESS_INDEX = 2;
	public static final int NUM_HSB = 3;
	public static final double BIAS = 1.0;// a common input used in neural networks
	public static final double SQRT2 = Math.sqrt(2); // Used for scaling distance from center	
	
	static {
		ActivationFunctions.resetFunctionSet();
		EvolutionaryHistory.initArchetype(0, "NONE");
	}
	
	private TWEANN tweann;

	public CPPNManipulator() {
		this(randomTWEANN(50));
	}

	/**
	 * @param mutations
	 */
	private static TWEANN randomTWEANN(int mutations) {
		// Generate random CPPN through several mutations
		TWEANNGenotype tg = new TWEANNGenotype();
		System.out.println(tg);
		for(int i = 0; i < mutations; i++) {
			System.out.println("mutation "+i);
			tg.mutate();
		}
		return tg.getPhenotype();
	}
	
	public CPPNManipulator(TWEANN tweann) {
		this.tweann = tweann;
	}
	
	@Override
	public Color getColorAtPoint(int x, int y, float brightness, Random random) {
		float[] hsb = getHSBFromCPPN(tweann, x, y, image.getWidth(), image.getHeight(), 0, 1.0, 0.0, 0.0, 0.0);
		//System.out.println(Arrays.toString(hsb));
//		maxB = Math.max(maxB, hsb[BRIGHTNESS_INDEX]);
//		minB = Math.min(minB, hsb[BRIGHTNESS_INDEX]);
//		if(Parameters.parameters.booleanParameter("blackAndWhitePicbreeder")) { // black and white
//			Color childColor = Color.getHSBColor(0, 0, hsb[BRIGHTNESS_INDEX]);
//			// set back to RGB to draw picture to JFrame
//			image.setRGB(x, y, childColor.getRGB());
//		} else { // Original Picbreeder color encoding
			Color childColor = Color.getHSBColor(hsb[HUE_INDEX], hsb[SATURATION_INDEX], hsb[BRIGHTNESS_INDEX]);
			// set back to RGB to draw picture to JFrame
			return childColor;
//			image.setRGB(x, y, childColor.getRGB());
//		}
	}
	
	public static float[] getHSBFromCPPN(TWEANN n, int x, int y, int imageWidth, int imageHeight, double time, double scale, double rotation, double deltaX, double deltaY) {

		double[] input = get2DObjectCPPNInputs(x, y, imageWidth, imageHeight, time, scale, rotation, deltaX, deltaY);

		// Multiplies the inputs of the pictures by the inputMultiples; used to turn on or off the effects in each picture
//		for(int i = 0; i < inputMultiples.length; i++) {
//			input[i] = input[i] * inputMultiples[i];
//		}

		// Eliminate recurrent activation for consistent images at all resolutions
		n.flush();
		return rangeRestrictHSB(n.process(input));
	}

	/**
	 * Gets scaled inputs to send to CPPN, using default scale of 1.0 
	 * and default rotation of 0.0 (no rotation and no scaling).
	 *
	 * @param x x-coordinate of pixel
	 * @param y y-coordinate of pixel
	 * @param imageWidth width of image
	 * @param imageHeight height of image
	 * @param time For animated images, the frame number (just use 0 for still images)
	 * @return array containing inputs for CPPN
	 */
	public static double[] get2DObjectCPPNInputs(int x, int y, int imageWidth, int imageHeight, double time) {
		return get2DObjectCPPNInputs(x,y,imageWidth,imageHeight,time,1.0, 0.0, 0.0, 0.0);
	}
	
	/**
	 * Gets scaled inputs to send to CPPN, using default scale of 1.0 
	 * and default rotation of 0.0 (no rotation and no scaling).  Same 
	 * as the get2DObjectCPPNInputs method above but contains two new 
	 * parameters, scale and rotation, used for rotating and scaling
	 * the image.
	 * 
	 * @param x x-coordinate of pixel
	 * @param y y-coordinate of pixel
	 * @param imageWidth width of image
	 * @param imageHeight height of image
	 * @param time For animated images, the frame number (just use 0 for still images)
	 * @param scale scale factor by which to scale the image
	 * @param rotation the degree in radians by which to rotate the image
	 * @param deltaX X coordinate of the center of the box
	 * @param deltaY Y coordinate of the center of the box
	 * @return array containing inputs for CPPN
	 */
	public static double[] get2DObjectCPPNInputs(int x, int y, int imageWidth, int imageHeight, double time, double scale, double rotation, double deltaX, double deltaY) {
		Tuple2D scaled = CartesianGeometricUtilities.centerAndScale(new Tuple2D(x, y), imageWidth, imageHeight);
		scaled = scaled.mult(scale);
		scaled = scaled.rotate(rotation);
		ILocated2D finalPoint = scaled.add(new Tuple2D (deltaX, deltaY));
		if(time == -1) { // default, single image. Do not care about time
			return new double[] { finalPoint.getX(), finalPoint.getY(), finalPoint.distance(new Tuple2D(0, 0)) * SQRT2, BIAS };
		} else { // TODO: May need to divide time by frame rate later
			return new double[] { finalPoint.getX(), finalPoint.getY(), finalPoint.distance(new Tuple2D(0, 0)) * SQRT2, time, BIAS };
		}
	}
	
	/**
	 * Given the direct HSB values from the CPPN (a double array), convert to a
	 * float array (required by Color methods) and do range restriction on
	 * certain values.
	 * 
	 * These range restrictions were stolen from Picbreeder code on GitHub
	 * (though not the original code), but 2 in 13 randomly mutated networks
	 * still produce boring black screens. Is there a way to fix this?
	 * 
	 * @param hsb array of HSB color information from CPPN
	 * @return scaled HSB information in float array
	 */
	public static float[] rangeRestrictHSB(double[] hsb) {
//		if(Parameters.parameters.booleanParameter("standardPicBreederHSBRestriction")) {
			// This is the original Picbreeder code that I have used for a while, but even the comment
			// above (from long ago) indicates problems with saturation. These are exacerbated with the
			// enhanced genotypes (especially when scale change is allowed), hence the alternative below.
			return new float[] { 
					(float) FullLinearPiecewiseFunction.fullLinear(hsb[HUE_INDEX]),
					(float) HalfLinearPiecewiseFunction.halfLinear(hsb[SATURATION_INDEX]),
					(float) Math.abs(FullLinearPiecewiseFunction.fullLinear(hsb[BRIGHTNESS_INDEX])) 
			};
//		} else {
//			// This new code is a possible alternative for enhanced genotypes
//			return new float[] { 
//					(float) ActivationFunctions.fullSawtooth(hsb[HUE_INDEX],2),	// Wraps around the Hue cylinder
//					(float) Math.abs(FullLinearPiecewiseFunction.fullLinear(hsb[SATURATION_INDEX])), // Smooth out transition from positive to negative
//					(float) Math.abs(FullLinearPiecewiseFunction.fullLinear(hsb[BRIGHTNESS_INDEX])) // No change
//			};
//		}
	}
}
