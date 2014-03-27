package engine;

/**
 * Interface for Player methods, how the player interacts with the environment, etc.
 * @author 929837
 *
 */
public interface Player {

	
	/**
	 * Shoot or melee with the current weapon
	 */
	public void fire();
	
	
	/**
	 * Switch weapons
	 */
	public void switchWeapons();
	
	/**
	 * Throw grenade or apply a powerup, such as a health pack
	 */
	public void useSpecial();
	
	/**
	 * Reload the current weapon
	 */
	public void reload();
	
	/**
	 * Crouch down, lowering the view of the camera and slowing the player's speed
	 */
	public void crouch();
	
	/**
	 * Temporarily move faster but you cannot shoot
	 */
	public void run();
	
	/**
	 * Perform a special action, such as opening a door (not available at all times)
	 */
	public void interact();
	
}
