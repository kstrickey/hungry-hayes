package resources.classes;

public class EuclidianVector {
	
	public double magnitude;
	public double direction;		// [0, 2pi]
	
	/**
	 * Default constructor: sets magnitude and direction to 0.
	 */
	public EuclidianVector() {
		magnitude = direction = 0;
	}
	
	/**
	 * Paramaterized constructor: sets magnitude and direction as passed in.
	 * Converts values to equal values in range: magnitude >= 0, 0 <= direction < 2pi.
	 * @param magnitude
	 * @param direction
	 */
	public EuclidianVector(double magnitude, double direction) {
		this.magnitude = magnitude;
		this.direction = direction;
		standardize();
	}
	
	/**
	 * Copy constructor: sets magnitude and direction equal to those of a given EuclidianVector.
	 * @param vector
	 */
	public EuclidianVector(EuclidianVector vector) {
		this(vector.magnitude, vector.direction);
	}
	
	/**
	 * Calculates the x-value of the vector in rectangular coordinates.
	 * @return this value as a double
	 */
	public double rectangularX() {
		return magnitude * Math.cos(direction);
	}

	/**
	 * Calculates the y-value of the vector in rectangular coordinates.
	 * @return this value as a double
	 */
	public double rectangularY() {
		return magnitude * Math.sin(direction);
	}
	
	/**
	 * Sets magnitude and direction based on rectangular dimensions given.
	 * @param x
	 * @param y
	 */
	public void setUsingRectangularDimensions(double x, double y) {
		magnitude = Math.sqrt(x*x + y*y);
		direction = Math.atan2(y, x);
		standardize();
	}
	
	/**
	 * Adds another EuclidianVector to this.
	 * @param otherVector
	 */
	public void addVector(EuclidianVector otherVector) {
		setUsingRectangularDimensions(rectangularX() + otherVector.rectangularX(), rectangularY() + otherVector.rectangularY());
	}
	
	/**
	 * Instantiates and returns a new EuclidianVector that is the sum of two given EuclidianVectors.
	 * The parameter EuclidianVectors are not modified.
	 * @param vector1
	 * @param vector2
	 * @return the vector sum of vector1 and vector2, as a EuclidianVector
	 */
	public static EuclidianVector addVectors(EuclidianVector vector1, EuclidianVector vector2) {
		EuclidianVector sum = new EuclidianVector();
		sum.setUsingRectangularDimensions(vector1.rectangularX() + vector2.rectangularX(), vector1.rectangularY() + vector2.rectangularY());
		return sum;
	}
	
	
	// NOTE: This function introduces a bug due to the inverse tangent range of -pi/2 to pi/2.
	// We can leave it out.
	/**
	 * Converts negative magnitudes to positive (modifying direction accordingly) and changes direction, if applicable, to within the range [0,2pi).
	 */
	private void standardize() {
//		if (magnitude < 0) {
//			magnitude *= -1;
//			direction += Math.PI;
//		}
//		
//		while (direction < 0) {
//			direction += Math.PI;
//		}
//		
//		while (direction >= 2 * Math.PI) {
//			direction -= Math.PI;
//		}
	}
	
}
