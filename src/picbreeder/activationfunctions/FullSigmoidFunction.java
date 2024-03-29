package picbreeder.activationfunctions;

//import org.nd4j.linalg.activations.Activation;

public class FullSigmoidFunction extends SigmoidFunction {

	/**
	 * Standard sigmoid, but stretched to the range
	 * -1 to 1. This is an alternative to tanh, and is
	 * also used in the original Picbreeder.
	 * @param x parameter
	 * @return result
	 */
	@Override
	public double f(double x) {
		return (2 * super.f(x)) - 1;
	}
	
//	@Override
//	public Activation equivalentDL4JFunction() {
//		throw new UnsupportedOperationException("No corresponding DL4J function for " + name());
//	}

	@Override
	public String name() {
		return "sigmoid-full"; //"Full Sigmoid";
	}
}
