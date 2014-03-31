package engine;

import javax.media.opengl.GL;

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

	public static void drawEnemy(double x, double y, GL myGL){
		//TODO make enemy here
	/*	myGL.glBegin(GL.GL_QUADS);
		myGL.glVertex3f(x,y, (float)World.getMap()[x][y]-10);
		myGL.glVertex3f(x+10,y, (float)World.getMap()[x][y]-10);*/
		
		myGL.glTranslated(x, y, (float)World.getMap()[(int) x][(int) y]);
	
		
	}


}
