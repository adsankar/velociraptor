package engine;

/**
 * Handles all of the mouse movements
 * @author Aleksander Sankar and Sohum Dalal
 * Software Design Pd. 7
 * Mr. Fowler
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
	 * @param f the new speed
	 */
	public void setSpeed(float f) {
		speed = f;
	}
	/**
	 * Sets the location of the mouse
	 * @param x the new x location
	 * @param y the new y location
	 */
	public void setMouse(float x, float y) {
		mx = x;
		my = y;
	}
	/**
	 * Moves the camera position in regards to the mouse.
	 * @param player the player you want to change the view of
	 */
	public void eye(Player player) {
		player.setView((float) (player.getXPosition() + (Math.sin(mx * .01f) * 3)),
				(-my * .01f) + player.getYPosition(),
				(float) (player.getZPosition() - (Math.cos(mx * .01f) * 3)));
	}//end eye
}//end class
