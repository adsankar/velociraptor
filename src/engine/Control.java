package engine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Handles the holding down of keys and mouse movements
 * @author Aleksander Sankar and Sohum Dalal
 * Software Design Pd. 7
 * Mr. Fowler
 */
public class Control {

	public ArrayList<Integer> keysDown = new ArrayList<Integer>(0);
	public ArrayList<Integer> keysOff = new ArrayList<Integer>(0);
	public float mouseX = 0;
	public float mouseY = 0;
	public float midX = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
	public float midY = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
	public Robot rob;
	/**
	 * Constructor that initializes the robot tool (used to move mouse)
	 */
	public Control() {
		try {
			rob=new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Handles keyEvents including when keys are pressed, or held down
	 * @param e
	 */
	public void pressKey(KeyEvent e) {
		boolean add = true;
		ArrayList<Integer> newDown = new ArrayList<Integer>(0);
		for (int i = 0; i < keysDown.size(); i++) {
			if (keysDown.get(i) == e.getKeyCode()) {
				add = false;
				break;
			}
			newDown.add(keysDown.get(i));
		}
		for (int i = 0; i < keysOff.size(); i++)
			if (keysOff.get(i) == e.getKeyCode())
				add = false;
		if (add) {
			newDown.add(e.getKeyCode());
			keysDown = newDown;
		}
	}
	/**
	 * Handles key events when key is released by user
	 * @param e
	 */
	public void releaseKey(KeyEvent e) {
		ArrayList<Integer> newDown = new ArrayList<Integer>(0);
		for (int i = 0; i < keysDown.size(); i++)
			if (keysDown.get(i) != e.getKeyCode())
				newDown.add(keysDown.get(i));
		keysDown = newDown;
		newDown = new ArrayList<Integer>(0);
		for (int i = 0; i < keysOff.size(); i++)
			if (keysOff.get(i) != e.getKeyCode())
				newDown.add(keysOff.get(i));
		keysOff = newDown;
	}
	/**
	 * Stops the keys from functioning (when game has ended) or when screen loses focus
	 */
	public void stopKeys() {
		keysDown = new ArrayList<Integer>(0);
	}
	/**
	 * Returns what keys are being pressed down
	 * @return keysDown the list of keys pressed down
	 */
	public ArrayList<Integer> getKeysDown() {
		return keysDown;
	}
	/**
	 * Sets the position of mouse
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void setMouse(float x, float y) {
		mouseX = x;
		mouseY = y;
	}
	/**
	 * Updates new location/position of camera and moves mouse back to center
	 * @param e
	 */
	public void move(MouseEvent e) {
		mouseX += e.getX()  - midX;
		mouseY += e.getY() - midY;
		rob.mouseMove((int) midX,(int) midY);
	}
	/**
	 * Gets the X position of the mouse
	 * @return the x position of the mouse
	 */
	public float getMouseX() {
		return mouseX;
	}
	/**
	 * Returns the Y postion of the mouse
	 * @return the y position of the mouse
	 */
	public float getMouseY() {
		return mouseY;
	}
}
