package engine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//TODO center mouse
public class Control {
	
	public ArrayList<Integer> keysDown = new ArrayList<Integer>(0);
	public ArrayList<Integer> keysOff = new ArrayList<Integer>(0);
	public float mouseX = 0;
	public float mouseY = 0;
	public float midX = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
	public float midY = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
	public Robot rob;
	
	public Control() {
		try {
			rob=new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void stopKeys() {
		keysDown = new ArrayList<Integer>(0);
	}
	
	public ArrayList<Integer> getKeysDown() {
		return keysDown;
	}
	
	public void setMouse(float x, float y) {
		mouseX = x;
		mouseY = y;
	}
	
	
	public void move(MouseEvent e) {
		mouseX += e.getX()  - midX;
		mouseY += e.getY() - midY;
		rob.mouseMove((int) midX,(int) midY);
		}
	
	public float getMouseX() {
		return mouseX;
	}
	
	public float getMouseY() {
		return mouseY;
	}
}