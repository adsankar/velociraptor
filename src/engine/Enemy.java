package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;

/**
 * The object that represents characters trying to attack the player
 * @author 929837
 *
 */
public class Enemy {

	private int damage;
	private int speed;
	private int xPosition;
	private int yPosition;
	private int attackRate;
	private boolean zigzag;
	private int health;
	private boolean gold;
	private static FloatBuffer enemyVert;
	private static FloatBuffer enemyNVert;


	/**
	 * Get the damage of the enemy
	 * @return damage
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Set the damage of the enemy
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Gets the speed of the enemy
	 * @return speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Sets the speed of the enemy
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getxPosition() {
		return xPosition;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public int getAttackRate() {
		return attackRate;
	}
	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}
	public boolean isZigzag() {
		return zigzag;
	}
	public void setZigzag(boolean zigzag) {
		this.zigzag = zigzag;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean isGold() {
		return gold;
	}
	public void setGold(boolean gold) {
		this.gold = gold;
	}

	public static void loadEnemyData(File myFile){
		ArrayList<Float> x = new ArrayList<Float>();
		ArrayList<Float> n = new ArrayList<Float>();
		FileInputStream myStream =null;
		try{ myStream = new FileInputStream(myFile);
		}catch (FileNotFoundException ex){
			System.out.println("No File Here!");
			System.exit(0);//end process
		}
		int count =0;
		Scanner myScanner = new Scanner(myStream);
		while (myScanner.hasNext()){
			count++;
			String temp;
			//float f1, f2, f3;
			temp = myScanner.nextLine().trim();
			if (temp.substring(0,2).equals("v ") && count>36){
				//String one = temp.substring(2,temp.indexOf(' '));
				temp = temp.substring(2,temp.length());
				int firstSpace = temp.indexOf(" ");
				int secondSpace = temp.lastIndexOf(" ");
				x.add(Float.parseFloat(temp.substring(0,firstSpace)));
				x.add(Float.parseFloat(temp.substring(firstSpace+1,secondSpace)));
				x.add(Float.parseFloat(temp.substring(secondSpace+1,temp.length())));
			}
			if (temp.substring(0,3).equals("vn ") && count>36){
				//String one = temp.substring(2,temp.indexOf(' '));
				temp = temp.substring(3,temp.length());
				int firstSpace = temp.indexOf(" ");
				int secondSpace = temp.lastIndexOf(" ");
				n.add(Float.parseFloat(temp.substring(0,firstSpace)));
				n.add(Float.parseFloat(temp.substring(firstSpace+1,secondSpace)));
				n.add(Float.parseFloat(temp.substring(secondSpace+1,temp.length())));
			}
		}


		enemyVert = BufferUtil.newFloatBuffer(x.size());
		for(Float f: x){
			enemyVert.put(f);
			//	System.out.println(f);
		}
		enemyVert.rewind();
		
		enemyNVert = BufferUtil.newFloatBuffer(n.size());
		for(Float f: n){
			enemyNVert.put(f);
			//	System.out.println(f);
		}
		enemyNVert.rewind();
	}
	
	public static void drawEnemy(double x, double y, GL myGL){
		//TODO make velociraptor here
		myGL.glColor3d(1,0,0);
		
		myGL.glTranslated(x, 5+(float)World.getMap()[(int) x][(int) y], y);
		
		
		/*
		myGL.glBegin(GL.GL_QUADS);
		myGL.glVertex3d(x,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		myGL.glVertex3d(x+5,y, (float)World.getMap()[(int) x][(int) y]-10);
		*/
		//TODO fix here
		/*myGL.glBegin(GL.GL_TRIANGLES);
		myGL.glVertex3d(1,1,1);
		myGL.glVertex3d(2,1,1);
		myGL.glVertex3d(1,2,2);
		//System.out.println("draw");
		myGL.glEnd();*/
		
	
		//myGL.glTranslated(x, y, (float)World.getMap()[(int) x][(int) y]);
		myGL.glColor3f(0.8f, 0,0);


		//	FloatBuffer colors = BufferUtil.newFloatBuffer(12*(height[0].length-1)*(height.length-1));
		//	FloatBuffer norms = BufferUtil.newFloatBuffer(12*(height[0].length-1)*(height.length-1));

		//	myGL.glEnableClientState(GL.GL_COLOR_ARRAY);
		myGL.glEnableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glEnableClientState(GL.GL_NORMAL_ARRAY);

			myGL.glNormalPointer(GL.GL_FLOAT, 0, enemyNVert);
		//	myGL.glColorPointer(3, GL.GL_FLOAT, 0, colors);
		myGL.glVertexPointer(3, GL.GL_FLOAT, 0, enemyVert);

		myGL.glDrawArrays(GL.GL_TRIANGLE_STRIP,0,enemyVert.capacity()/3);

		//disable arrays when done
		myGL.glDisableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glDisableClientState(GL.GL_NORMAL_ARRAY);

		/*	myGL.glVertex3d(0,0,0);
		myGL.glVertex3d(0,10,0);
		myGL.glVertex3d(10,0,0);
		myGL.glVertex3d(10,10,0);*/
		

		myGL.glEnd();
		
	}


}
