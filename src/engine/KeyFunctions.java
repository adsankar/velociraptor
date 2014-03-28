

package engine;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Handles key functions used during program
 * @author 930983
 *
 */
public class KeyFunctions {

	private final float MOVE_SMOOTH = 0.4f;
	private boolean move = true;
	private float step = 0;
	public float speed = 1;
	public float moveX = 25000;
	public float moveY = 0;
	public float moveZ = 25000;
	public float mx = 0;
	public float my = 0;
	boolean landed = true;
	double[][] map;
	Eye refer = new Eye();
	MP3 jet = new MP3("/Soundtrack/Jet.mp3");
	/**
	 * Creates a new instance of KeyFunctions
	 */
	public KeyFunctions() {
	}
	/**
	 * Sets the moving speed of the character in regard to keys being pressed
	 * @param f
	 */
	public void setSpeed(float f) {
		speed = f;
	}
	/**
	 * Sets up the map
	 * @param m
	 */
	public void setMap(double[][] m) {
		map = m;
	}
	/**
	 * Handles all key inputs (if key is pressed, result is what happens)
	 * @param keys
	 */
	public void processKeys(ArrayList<Integer> keys) {
		boolean run = false;
		for(int i = 0; i < keys.size(); i++) {
			float sin = (float) Math.sin(mx * .01) * 3 * speed;
			float cos = (float) Math.cos(mx * .01) * 3 * speed;
			if(keys.get(i) == KeyEvent.VK_SHIFT) {
				run = true;
			}
			if(run) {
				sin *= 2;
				cos *= 2;
			}
			if(keys.get(i) == KeyEvent.VK_W) {
				moveZ -= cos;
				moveX += sin;
			}
			if(keys.get(i) == KeyEvent.VK_S) {
				moveZ += cos;
				moveX -= sin;
			}
			if(keys.get(i) == KeyEvent.VK_D) {
				moveZ += sin;
				moveX += cos;
			}
			if(keys.get(i) == KeyEvent.VK_A) {
				moveZ -= sin;
				moveX -= cos;
			}

			if(keys.get(i) == KeyEvent.VK_E) {
				moveY += speed * .005;
				landed = false;
				if(!jet.isPlaying())
					jet.play();
			}
			if(keys.get(i) == KeyEvent.VK_Q)
				moveY -= speed * .005;

			if (keys.get(i)== KeyEvent.VK_SPACE){
				jump();
			}

			if(moveX / 500 < 0)
				moveX = 0;
			if(moveX / 500 > 100)
				moveX = 50000;
			if(moveZ / 500 < 0)
				moveZ = 0;
			if(moveZ / 500 > 100)
				moveZ = 50000;

			if(step > 0) {
				step -= 1;
				move = false;
			}
			if(step == 0)
				move = true;

			if((keys.get(i) == KeyEvent.VK_W ||
					keys.get(i) == KeyEvent.VK_S ||
					keys.get(i) == KeyEvent.VK_D ||
					keys.get(i) == KeyEvent.VK_A) &&
					move && landed)
				if(refer.getHeight() > -2.5)
					walk("Walk");
				else
					walk("Swim");
		}
	}
	/**
	 * Allows character to jump
	 */
	public void jump(){	
		//	moveY += speed * .005;	
	}
	/**
	 * Allows character to crouch down
	 */
	public void crouch(){

	}
	/**
	 * Sets the position of the mouse
	 * @param x
	 * @param y
	 */
	public void setMouse(float x, float y) {
		mx = x;
		my = y;
	}
	/**
	 * Changes camera location (eye) of character in regards to key interaction
	 * @param eye
	 */
	public void moveEye(Eye eye) {
		int x = (int) (moveX / 500);
		int z = (int) (moveZ / 500);

		float bottomLeft = (float) map[x][z];
		float bottomRight = (float) map[x + 1][z];
		float topLeft = (float) map[x][z + 1];
		float topRight = (float) map[x + 1][z + 1];
		float height = MOVE_SMOOTH*((bottomLeft+bottomRight+topLeft+topRight)/4 +5)+
				(1-MOVE_SMOOTH)*eye.getHeight();

		if(height < -5)
			height = -5f;
		eye.setHeight(height);

		if(moveY < eye.getHeight() && !landed) {
			landed = true;
			jet.close();
			if(height > -2.5)
				walk("Land");
			else
				walk("Splash");
		}
		if(landed)
			moveY = eye.getHeight();

		eye.setPosition(moveX / 500, moveY, (moveZ / 500));

		refer = eye;
	}
	/**
	 * Plays the music and allows the character to walk
	 * @param str
	 */
	public void walk(String str) {
		play(str + ".wav");
		step = 150;
	}
	/**
	 * Plays the music
	 * @param filename
	 */
	public static void play(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
		}
		catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}	 
