

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
	//private boolean move = true;
	private float step = 0;
	public float speed = 1;
	public float moveX = 25000;
	public float moveY = 0;
	public float moveZ = 25000;
	public float mx = 0;
	public float my = 0;
	public float gravity =0;
	private boolean landed = true;
	private boolean jump = false;
	double[][] map;
	Player refer = new Player();
	MP3 jet = new MP3("/Soundtrack/Jet.mp3");
	/**
	 * Creates a new instance of KeyFunctions
	 */
	public KeyFunctions() {
		//do nothing
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
		boolean fly = false;
		boolean crouched = false;
		for(int i = 0; i < keys.size(); i++) {
			float sin = (float) Math.sin(mx * .01) * 3 * speed;
			float cos = (float) Math.cos(mx * .01) * 3 * speed;
			for (int j = 0; j < keys.size(); j++){
				if(keys.get(j) == KeyEvent.VK_SHIFT) {
					run = true;
				}
			}
			if(run) {
				sin *= 2;
				cos *= 2;
			}
			for (int j = 0; j < keys.size(); j++){
				if(keys.get(j) == KeyEvent.VK_C) {
					crouched = true;
				}
			}
			if(crouched) {
				sin /= 2;
				cos /= 2;
				moveY -= 10;
			}
			//else moveY = 0;
			if(keys.get(i) == KeyEvent.VK_W || keys.get(i) == KeyEvent.VK_UP) {
				moveZ -= 1.8*cos;
				moveX += 1.8*sin;
			}
			if(keys.get(i) == KeyEvent.VK_SPACE && !fly) {
				if(landed && !jump) {
					jump = true;
					gravity = .15f * (speed / 20);
				}
				if(!landed && !jump) {
					jump = true;
					gravity = 0;
					jet.close();
				}
			}

			if(keys.get(i) == KeyEvent.VK_S || keys.get(i) == KeyEvent.VK_DOWN && !((keys.get(i) == KeyEvent.VK_S) && keys.get(i) == KeyEvent.VK_DOWN)) {
				moveZ += 1.8*cos;
				moveX -= 1.8*sin;
			}
			if(keys.get(i) == KeyEvent.VK_D || keys.get(i) == KeyEvent.VK_RIGHT) {
				moveZ += sin;
				moveX += cos;
			}
			if(keys.get(i) == KeyEvent.VK_A || keys.get(i) == KeyEvent.VK_LEFT) {
				moveZ -= sin;
				moveX -= cos;
			}

			if(keys.get(i) == KeyEvent.VK_R) {
				Player.setAmmo(100);
			}

			if(keys.get(i) == KeyEvent.VK_E) {
				moveY += speed * .005;
				landed = false;
				if(!jet.isPlaying())
					jet.play();
			}
			if(keys.get(i) == KeyEvent.VK_Q)
				moveY -= speed * .005;

			if (keys.get(i)== KeyEvent.VK_C){
				Player.crouch();
			}


			if(moveX / 500 < 0)
				moveX = 0;
			if(moveX / 500 > 256)
				moveX = 128000;
			if(moveZ / 500 < 0)
				moveZ = 0;
			if(moveZ / 500 > 256)
				moveZ = 128000;

			if(step > 0) {
				step -= 1;
				//move = false;
			}
			if(step == 0)
				//move = true;

				if((keys.get(i) == KeyEvent.VK_W ||
				keys.get(i) == KeyEvent.VK_S ||
				keys.get(i) == KeyEvent.VK_D ||
				keys.get(i) == KeyEvent.VK_A) &&landed && !jump)
					if(refer.getHeight() > -1)
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
	 * @param player
	 */
	public void moveEye(Player player) {
		int x = (int) (moveX / 500);
		int z = (int) (moveZ / 500);

		if (x+1 >= map.length || z+1 > map.length){
			return;
		}
		float bottomLeft = (float) map[x][z];
		float bottomRight = (float) map[x + 1][z];
		float topLeft = (float) map[x][z + 1];
		float topRight = (float) map[x + 1][z + 1];
		float height = MOVE_SMOOTH*((bottomLeft+bottomRight+topLeft+topRight)/4 +5)+
				(1-MOVE_SMOOTH)*player.getHeight();

		if(height < -40)
			height = -40f;
		player.setHeight(height);

		if(moveY < player.getHeight() && (!landed ||jump)) {
			landed = true;
			jump =false;
			jet.close();
			if(height > -1)
				walk("Land");
			else
				walk("Splash");
		}
		if(landed && !jump)
			moveY = player.getHeight();

		player.setPosition(moveX / 500, moveY, (moveZ / 500));

		refer = player;
	}
	/**
	 * Plays the music and allows the character to walk
	 * @param str
	 */
	public void walk(String str) {
		play(str + ".wav");
		step = 20;
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
