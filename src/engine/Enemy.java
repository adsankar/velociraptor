package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;

/**
 * The object that represents characters trying to attack the player
 * @author Aleksander Sankar and Sohum Dalal
 * Software Design Pd. 7
 * Mr. Fowler
 */
public class Enemy {

	private int damage;
	private int speed;
	private int xPosition;
	private int yPosition;
	private int attackRate;
	private boolean zigzag;
	private int health;
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

	/**
	 * Get the x position of the enemy
	 * @return the x positon of the enemy
	 */
	public int getxPosition() {
		return xPosition;
	}

	/**
	 * Set the x position of the enemy
	 * @param xPosition the new x position
	 */
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Get the y position of the enemy
	 * @return the y position of the enemy
	 */
	public int getyPosition() {
		return yPosition;
	}

	/**
	 * Set the y postion of the enemy
	 * @param yPosition the new y position
	 */
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * Get the attack rate
	 * @return attackRate
	 */
	public int getAttackRate() {
		return attackRate;
	}

	/**
	 * Set the attack rate
	 * @param attackRate
	 */
	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}

	/**
	 * Check to see if the enemy is zigzagging
	 * @return zigzag
	 */
	public boolean isZigzag() {
		return zigzag;
	}

	/**
	 * Turn the zigzag on or off
	 * @param zigzag
	 */
	public void setZigzag(boolean zigzag) {
		this.zigzag = zigzag;
	}

	/**
	 * Get the health of the enemy
	 * @return health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set the health of the enemy
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Load the enemy vertex data from a file
	 * @param myFile
	 */
	public static void loadEnemyData(File myFile){
		ArrayList<Float> x = new ArrayList<Float>();
		ArrayList<Float> n = new ArrayList<Float>();
		FileInputStream myStream =null;
		try{ myStream = new FileInputStream(myFile);
		}catch (FileNotFoundException ex){
			System.out.println("No File Here!");
			System.exit(0);//end process
		}//end catch
		int count =0;
		Scanner myScanner = new Scanner(myStream);
		while (myScanner.hasNext()){
			count++;
			String temp;
			temp = myScanner.nextLine().trim();
			if (temp.substring(0,2).equals("v ") && count>36){

				temp = temp.substring(2,temp.length());
				int firstSpace = temp.indexOf(" ");
				int secondSpace = temp.lastIndexOf(" ");
				x.add(Float.parseFloat(temp.substring(0,firstSpace)));
				x.add(Float.parseFloat(temp.substring(firstSpace+1,secondSpace)));
				x.add(Float.parseFloat(temp.substring(secondSpace+1,temp.length())));
			}//end if
			if (temp.substring(0,3).equals("vn ") && count>36){

				temp = temp.substring(3,temp.length());
				int firstSpace = temp.indexOf(" ");
				int secondSpace = temp.lastIndexOf(" ");
				n.add(Float.parseFloat(temp.substring(0,firstSpace)));
				n.add(Float.parseFloat(temp.substring(firstSpace+1,secondSpace)));
				n.add(Float.parseFloat(temp.substring(secondSpace+1,temp.length())));
			}//end if
		}//end while

		//create vertex buffer and add elements to it
		enemyVert = BufferUtil.newFloatBuffer(x.size());
		for(Float f: x){
			enemyVert.put(f);

		}//end for

		enemyVert.rewind();

		enemyNVert = BufferUtil.newFloatBuffer(n.size());
		for(Float f: n){
			enemyNVert.put(f);

		}//end for


	}//end loadEnemyData


	/**
	 * Draw the enemy at a specific location on the map
	 * @param x the x location
	 * @param y the y location
	 * @param myGL 
	 */
	public static void drawEnemy(double x, double y, GL myGL){

		myGL.glColor3d(1,0,0);

		myGL.glTranslated(x, 5+(float)World.getMap()[(int) x][(int) y], y);


		myGL.glColor3f(0.8f, 0,0);

		myGL.glEnableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glEnableClientState(GL.GL_NORMAL_ARRAY);

		myGL.glNormalPointer(GL.GL_FLOAT, 0, enemyNVert);
		myGL.glVertexPointer(3, GL.GL_FLOAT, 0, enemyVert);

		myGL.glDrawArrays(GL.GL_TRIANGLE_STRIP,0,enemyVert.capacity()/3);

		//disable arrays when done
		myGL.glDisableClientState(GL.GL_VERTEX_ARRAY);
		myGL.glDisableClientState(GL.GL_NORMAL_ARRAY);

		myGL.glEnd();

	}//end drawEnemy
}//end class
