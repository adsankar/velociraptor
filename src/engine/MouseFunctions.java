
package engine;
/**
 * handles the mouse movements
 * @author 930983
 *
 */

public class MouseFunctions {

	public float mx;
	public float my;
	public float speed = 1;
	/**
	 * New instance of Mouse Functions
	 */
	public MouseFunctions() {
	}
	/**
	 * Sets the speed of movement for character in regards to how much the user moves the mouse
	 * @param f
	 */
	public void setSpeed(float f) {
		speed = f;
	}
	/**
	 * Sets the location of the mouse
	 * @param x
	 * @param y
	 */
	public void setMouse(float x, float y) {
		mx = x;
		my = y;
	}
	/**
	 * Moves the camera position in regards to the mouse.
	 * @param player
	 * @return
	 */
	public void eye(Player player) {
		player.setView((float) (player.getXPosition() + (Math.sin(mx * .01f) * 3)),
				(-my * .01f) + player.getYPosition(),
				(float) (player.getZPosition() - (Math.cos(mx * .01f) * 3)));
	}
}