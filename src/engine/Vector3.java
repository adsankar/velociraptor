package engine;

/**
 * Class for storing 3D vectors
 * @author Aleksander Sankar and Sohum Dalal
 * Software Design Pd. 7
 * Mr. Fowler
 */
public class Vector3 {

	private float x;
	private float y;
	private float z;

	/**
	 * Default constructor which creates a vector of <0,0,0>
	 */
	public Vector3(){
		x=0;
		y=0;
		z=0;
	}

	/**
	 * Constructor which creates a vector specified by the 3 parameters
	 * @param newX i component of the vector
	 * @param newY j component of the vector
	 * @param newZ k component of the vector
	 */
	public Vector3(float newX, float newY, float newZ){
		x = newX;
		y = newY;
		z = newZ;
	}

	/**
	 * Cross product of two vectors that are Vector3 objects
	 * @param a the first vector
	 * @param b the second vector
	 * @return the cross product of the two vectors
	 */
	public static Vector3 cross(Vector3 a, Vector3 b){
		return new Vector3(a.getY()*b.getZ()-a.getZ()*b.getY(),
				a.getZ()*b.getX()-a.getX()*b.getZ(),
				a.getX()*b.getY()-a.getY()*b.getX());
	}

	/**
	 * Vector subtraction calculation
	 * @param a the first vector
	 * @param b the second vector
	 * @return < a > - < b > vector
	 */
	public static Vector3 vectorSubtract(Vector3 a, Vector3 b){
		return new Vector3(b.getX()-a.getX(),b.getY()-a.getY(),b.getZ()-a.getZ());
	}																		


	/**
	 * Calculate the average normal of a point with regard to the four points surrounding it
	 * @param a the first vector
	 * @param b the second vector
	 * @param c the middle (reference) vector
	 * @param d the third vector
	 * @param e the fourth vector
	 *     b
	 *   a c d
	 *     e
	 * @return a normalized average normal vector
	 */
	public static Vector3 calcAvgNormal(Vector3 a, Vector3 b, Vector3 c, Vector3 d, Vector3 e){
		//sub
		Vector3 ca = vectorSubtract(c, a);
		Vector3 cb = vectorSubtract(c, b);
		Vector3 cd = vectorSubtract(c, d);
		Vector3 ce = vectorSubtract(c, e);

		Vector3 acb = cross(ca,cb);
		Vector3 bce = cross(cb,cd);
		Vector3 ecd = cross(cd, ce);
		Vector3 dca = cross(ce, ca);

		float avgX = (acb.getX()+bce.getX()+ecd.getX()+dca.getX())/4;
		float avgY = (acb.getY()+bce.getY()+ecd.getY()+dca.getY())/4;
		float avgZ = (acb.getZ()+bce.getZ()+ecd.getZ()+dca.getZ())/4;
		return normalize(new Vector3(avgX, avgY, avgZ));

	}//end calcAvgNormal

	/**
	 * Normalize a vector
	 * @param a the vector to normalize
	 * @return a vector with magnitude 1
	 */
	public static Vector3 normalize(Vector3 a){
		float d = (float) Math.sqrt(a.getX()*a.getX()+a.getY()*a.getY()+a.getZ()*a.getZ());
		return new Vector3(a.getX()/d, a.getY()/d, a.getZ()/d);
	}//end normalize

	/**
	 * Get the x component of the vector
	 * @return x
	 */
	public float getX() {
		return x;
	}

	/**
	 * Set the x component of the vector
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Get the y component of the vector
	 * @return y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Set the y component of the vector
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Get the z component of the vector
	 * @return z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Set the z component of the vector
	 * @param z
	 */
	public void setZ(float z) {
		this.z = z;
	}

}//end class
