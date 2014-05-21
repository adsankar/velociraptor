package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

/**
 * Handles key functions used during program
 * @author Aleksander Sankar and Sohum Dalal
 * Software Design Pd. 7
 * Mr. Fowler
 */
public class KeyFunctions {

	private final float MOVE_SMOOTH = 0.4f;
	private final int walkDelay = 400;
	private final int runDelay = 300;

	private boolean run = false;

	private float step = 0;
	public float speed = 1;
	public float moveX = 25000;
	public float moveY = 0;
	public float moveZ = 25000;
	public float mx = 0;
	public float my = 0;
	public float gravity =-2f;
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
	}//end constructor

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
		for(int i = 0; i < keys.size(); i++) {
			float sin = (float) Math.sin(mx * .01) * 3 * speed;
			float cos = (float) Math.cos(mx * .01) * 3 * speed;
			if(refer.getHeight()<5) {
				sin /= 4;
				cos /= 4;
			}//end if
			for (int j = 0; j < keys.size(); j++){
				if(keys.get(j) == KeyEvent.VK_SHIFT) {
					//TODO run sounds
					sin *= 2;
					cos *= 2;
					run = true;
				}//end if
				run=false;
			}//end for

			for (int j = 0; j < keys.size(); j++){
				if(keys.get(j) == KeyEvent.VK_C) {
					sin /= 2;
					cos /= 2;
					moveY/=2;
				}//end if
			}//end for
			//else moveY = 0;
			if(keys.get(i) == KeyEvent.VK_W || keys.get(i) == KeyEvent.VK_UP) {
				moveZ -= 1.8*cos;
				moveX += 1.8*sin;
			}//end if
			/*if(keys.get(i) == KeyEvent.VK_SPACE ) {
				if(landed && !jump) {
					jump = true;
					landed=false;
					//gravity = -.2f;
					jumpSpeed=2;
				}//end if
				if(!landed && !jump) {
					jump = true;
					jumpSpeed+=gravity;
					jet.close();
				}//end if
				if (jump && !landed){
					moveY+=jumpSpeed;
				}//end if
			}//end if
			 */			//jumpSpeed=0;

			if(keys.get(i) == KeyEvent.VK_S ) {
				moveZ += .8*cos;
				moveX -= .8*sin;
			}//end if
			if(keys.get(i) == KeyEvent.VK_D ) {
				moveZ += sin;
				moveX += cos;
			}//end if
			if(keys.get(i) == KeyEvent.VK_A) {
				moveZ -= sin;
				moveX -= cos;
			}//end if

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
			}//end if
			if(step == 0)


				if((keys.get(i) == KeyEvent.VK_W ||
				keys.get(i) == KeyEvent.VK_S ||
				keys.get(i) == KeyEvent.VK_D ||
				keys.get(i) == KeyEvent.VK_A) && landed && !jump){
					if(refer.getHeight() > 5){
						walk("Walk");
						step = 20;
					}
					else{
						walk("Swim");
						step = 40;
					}
			
				}//end if
			
		}//end for

	}//end processKeys


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


		if (x+1 >= map.length || z+1 >= map.length){
			return;  
		}
		float bottomLeft = (float) map[x][z];
		float bottomRight = (float) map[x + 1][z];
		float topLeft = (float) map[x][z + 1];
		float topRight = (float) map[x + 1][z + 1];
		float height = MOVE_SMOOTH*((bottomLeft+bottomRight+topLeft+topRight)/4 +5)+
				(1-MOVE_SMOOTH)*player.getHeight();//smooth out the changes in altitude

		if(height < -40)
			height = -40f;
		player.setHeight(height);

		if(moveY < player.getHeight() && (!landed || jump)) {
			landed = true;
			jump =false;
			jet.close();
			if(height > 5)
				walk("Land");//play walk sound  when on land
			else
				walk("Splash"); //play splash sound on water
		}//end if
		if(!jump && landed)
			moveY = player.getHeight();

		player.setPosition(moveX / 500, moveY, (moveZ / 500));//set the position of the player

		refer = player;
	}//end moveEye
	/**
	 * Plays the music and allows the character to walk
	 * @param str
	 */
	public void walk(String str) {
		play(str + ".wav");
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
	}//end play
}//end class