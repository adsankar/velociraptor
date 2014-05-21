package engine;

/**
 * Interface for Player methods, how the player interacts with the environment, camera controls etc.
 * @author Aleksander Sankar and Sohum Dalal
 * Software Design Pd. 7
 * Mr. Fowler
 */
public class Player {

	private static int clip;
	private static int ammo;
	private static int health;
	private static int weaponNumber;

//	private static boolean isRunning;
	private static boolean isCrouched;
	public float atX = 0;
	public float atY = 0;
	public float atZ = 0;
	public float lookX = 0;
	public float lookY = 0;
	public float lookZ = 0;
	public float height = 0;
	public float force = 0;

	/**
	 * Spawn the player with a specific amount of ammo and health
	 */
	public Player(){
		clip = 3;
		ammo=10;
		health=100;
		weaponNumber=1;
		//isRunning=false;
		isCrouched=false;
	}

	/**
	 * Shoot or melee with the current weapon
	 */
	public void fire(){

		if (ammo>0){
			ammo--;
		}
	}


	/**
	 * Switch weapons
	 */
	public void switchWeapons(int direction){
		if (direction>0 && weaponNumber<2){
			weaponNumber++;
		}
		if (direction<0 && weaponNumber>0){
			weaponNumber--;
		}
	}



	/**
	 * Throw grenade or apply a powerup, such as a health pack
	 */
	public void useSpecial(){

	}

	/**
	 * Reload the current weapon
	 */
	public void reload(){
		if (clip>0){
			clip--;
			ammo += (10-ammo);
		}
	}

	/**
	 * Get the clip of the player
	 * @return clip
	 */
	public static int getClip() {
		return clip;
	}

	/**
	 * Set the clip of the player
	 * @param clip
	 */
	public static void setClip(int clip) {
		Player.clip = clip;
	}

	/**
	 * Set the height of the player
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Crouch down, lowering the view of the camera and slowing the player's speed
	 */
	public static void crouch(){
		isCrouched = !isCrouched;
	}

	/**
	 * Get the ammo of the player
	 * @return ammo
	 */
	public static int getAmmo() {
		return ammo;
	}

	/**
	 * Set the ammo of the player
	 * @param ammo
	 */
	public static void setAmmo(int ammo) {
		Player.ammo = ammo;
	}

	/**
	 * Get the health of the player
	 * @return health 
	 */
	public static int getHealth() {
		return health;
	}

	/**
	 * Set the health of the player
	 * @param health
	 */
	public static void setHealth(int health) {
		Player.health = health;
	}

	/**
	 * Get the weapon number of the player
	 * @return weaponNumber
	 */
	public static int getWeaponNumber() {
		return weaponNumber;
	}

	/**
	 * Set the weapon number of the player
	 * @param weaponNumber
	 */
	public static void setWeaponNumber(int weaponNumber) {
		Player.weaponNumber = weaponNumber;
	}

	/**
	 * Sets the position of the camera
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(float x, float y, float z) {
		atX = x;
		atY = y;
		atZ = z;
	}
	/**
	 * Sets where the camera is looking
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setView(float x, float y, float z) {
		lookX = x;
		lookY = y;
		lookZ = z;
	}
	/**
	 * Sets the height of the camera
	 * @param h
	 */
	public void setHeight(double h) {
		height = (float) h;
	}
	/**
	 * Represents the force at which the character moves
	 * @param f
	 */
	public void setForce(float f) {
		force = f;
	}
	/**
	 * Finds the X-Position of the camera
	 * @return atX
	 */
	public float getXPosition() {
		return atX;
	}
	/**
	 * Finds the Y position of the character
	 * @return atY
	 */
	public float getYPosition() {
		return atY;
	}
	/**
	 * Finds the Z position of the character
	 * @return atZ
	 */
	public float getZPosition() {
		return atZ;
	}

	/**
	 * Finds the X position of where the character is looking
	 * @return lookX
	 */
	public float getXView() {
		return lookX;
	}

	/**
	 * Finds the Y position of where the character is looking
	 * @return lookY
	 */
	public float getYView() {
		return lookY;
	}

	/**
	 * Finds the Z position of where the character is looking
	 * @return lookZ
	 */
	public float getZView() {
		return lookZ;
	}

	/**
	 * Finds the height of the camera
	 * @return height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Finds the force (like gravity)
	 * @return force
	 */
	public float getForce() {
		return force;
	}
}//end class
