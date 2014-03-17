package engine;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class KeyFunctions {
	
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
	
	public KeyFunctions() {
	}
	
	public void setSpeed(float f) {
		speed = f;
	}
	
	public void setMap(double[][] m) {
		map = m;
	}
	
	public void processKeys(ArrayList<Integer> keys) {
		for(int i = 0; i < keys.size(); i++) {
			float sin = (float) Math.sin(mx * .01) * 3 * speed;
			float cos = (float) Math.cos(mx * .01) * 3 * speed;
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
	
	public void jump(){
	
		
	//	moveY += speed * .005;
	
		
		
		
	}

	
	public void setMouse(float x, float y) {
		mx = x;
		my = y;
	}
	
	public Eye moveEye(Eye eye) {
		
		int x = (int) (moveX / 500);
		int z = (int) (moveZ / 500);
		
		float bl = (float) map[x][z];
		float br = (float) map[x + 1][z];
		float tl = (float) map[x][z + 1];
		float tr = (float) map[x + 1][z + 1];
		float rl = (moveX - (x * 500));
		float tb = (moveZ - (z * 500));
		float height = ((bl * (1 - rl) * (1 - tb)) + (br * rl * (1 - tb)) +
				(tl * (1 - rl) * tb) + (tr * rl * tb)) / 4;
		
		height = (float) map[x][z] + 5;
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
		
		return eye;
	}
	
	void walk(String str) {
		play(str + ".wav");
		step = 150;
	}
	
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