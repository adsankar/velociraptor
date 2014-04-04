package engine;

/**
 * look here
 * Interface for Player methods, how the player interacts with the environment, etc.
 * @author 929837
 *
 */
public class Player {

	private static int ammo;
	private static int health;
	private static int weaponNumber;
	private static boolean isRunning;
	private static boolean canInteract;
	private static boolean isCrouched;
	private static boolean canShoot;
	public float atX = 0;
	public float atY = 0;
	public float atZ = 0;
	public float lookX = 0;
	public float lookY = 0;
	public float lookZ = 0;
	public float height = 0;
	public float force = 0;
	
	
	public Player(){
		ammo=100;
		health=100;
		weaponNumber=1;
		isRunning=false;
		canInteract=false;
		isCrouched=false;
		canShoot=true;
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
		ammo=100;
	}
	
	/**
	 * Crouch down, lowering the view of the camera and slowing the player's speed
	 */
	public static void crouch(){
		isCrouched = !isCrouched;
	}
	
	/**
	 * Temporarily move faster but you cannot shoot
	 */
	public void run(){
		isRunning = !isRunning;
		canShoot = !canShoot;
	}
	
	/**
	 * Perform a special action, such as opening a door (not available at all times)
	 */
	public void interact(){
		if (canInteract){
			//something
		}
		canShoot=false;
	}
	
	public static int getAmmo() {
		return ammo;
	}

	public static void setAmmo(int ammo) {
		Player.ammo = ammo;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int health) {
		Player.health = health;
	}

	public static int getWeaponNumber() {
		return weaponNumber;
	}

	public static void setWeaponNumber(int weaponNumber) {
		Player.weaponNumber = weaponNumber;
	}

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		Player.isRunning = isRunning;
	}

	public static boolean isCanInteract() {
		return canInteract;
	}

	public static void setCanInteract(boolean canInteract) {
		Player.canInteract = canInteract;
	}

	public static boolean isCrouched() {
		return isCrouched;
	}

	public static void setCrouched(boolean isCrouched) {
		Player.isCrouched = isCrouched;
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
	 * @return
	 */
	public float getXPosition() {
		return atX;
	}
	/**
	 * Finds the Y position of the character
	 * @return
	 */
	public float getYPosition() {
		return atY;
	}
	/**
	 * Finds the Z position of the character
	 * @return
	 */
	public float getZPosition() {
		return atZ;
	}

	/**
	 * Finds the X position of where the character is looking
	 * @return
	 */
	public float getXView() {
		return lookX;
	}

	/**
	 * Finds the Y position of where the character is looking
	 * @return
	 */
	public float getYView() {
		return lookY;
	}

	/**
	 * Finds the Z position of where the character is looking
	 * @return
	 */
	public float getZView() {
		return lookZ;
	}

	/**
	 * Finds the height of the camera
	 * @return
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Finds the force (like gravity)
	 * @return
	 */
	public float getForce() {
		return force;
	}
}
