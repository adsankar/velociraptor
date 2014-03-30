package engine;

/**
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
	
	public Player(){
		ammo=100;
		health=100;
		weaponNumber=1;
		isRunning=false;
		canInteract=false;
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
		ammo=100;
	}
	
	/**
	 * Crouch down, lowering the view of the camera and slowing the player's speed
	 */
	public void crouch(){
		isCrouched = !isCrouched;
	}
	
	/**
	 * Temporarily move faster but you cannot shoot
	 */
	public void run(){
		isRunning = !isRunning;
	}
	
	/**
	 * Perform a special action, such as opening a door (not available at all times)
	 */
	public void interact(){
		if (canInteract){
			//something
		}
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
}
