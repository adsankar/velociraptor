
package engine;
/**
 * Determines where the camera is looking
 * @author 930983
 *
 */
//TODO possibly combine with Player
public class Eye {

	public float atX = 0;
	public float atY = 0;
	public float atZ = 0;
	public float lookX = 0;
	public float lookY = 0;
	public float lookZ = 0;
	public float height = 0;
	public float force = 0;

	/**
	 * Creates new instance of eye
	 */
	public Eye() {
	}
	/**
	 * Sets the position of the camera
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(float x, float y, float z) {
		atX = x;
		atY = y;
		atZ = z;
	}
	/**
	 * Sets where the camera is looking
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setView(float x, float y, float z) {
		lookX = x;
		lookY = y;
		lookZ = z;
	}
	/**
	 * Sets the height of the camera
	 * @param h
	 */
	public void setHeight(double h) {
		height = (float) h;
	}
	/**
	 * Represents the force at which the character moves
	 * @param f
	 */
	public void setForce(float f) {
		force = f;
	}
	/**
	 * Finds the X-Position of the camera
	 * @return
	 */
	public float getXPosition() {
		return atX;
	}
	/**
	 * Finds the Y position of the character
	 * @return
	 */
	public float getYPosition() {
		return atY;
	}
	/**
	 * Finds the Z position of the character
	 * @return
	 */
	public float getZPosition() {
		return atZ;
	}

	/**
	 * Finds the X position of where the character is looking
	 * @return
	 */
	public float getXView() {
		return lookX;
	}

	/**
	 * Finds the Y position of where the character is looking
	 * @return
	 */
	public float getYView() {
		return lookY;
	}

	/**
	 * Finds the Z position of where the character is looking
	 * @return
	 */
	public float getZView() {
		return lookZ;
	}

	/**
	 * Finds the height of the camera
	 * @return
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Finds the force (like gravity)
	 * @return
	 */
	public float getForce() {
		return force;
	}
}
