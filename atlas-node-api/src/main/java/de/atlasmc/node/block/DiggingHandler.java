package de.atlasmc.node.block;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.world.World;
import de.atlasmc.tick.Tickable;

/**
 * Handles the digging of a {@link Block}
 */
public interface DiggingHandler extends Tickable {
	
	/**
	 * Returns the current digging progress in percent where 1 represent 100 percent
	 * @return progress
	 */
	float getProgress();
	
	/**
	 * Sets the progress for the a current digging action with a value between 0 and 1.
	 * Will be ignored if no digging is in progress
	 * @param value progress between 0 and 1
	 */
	void setProgress(float value);
	
	/**
	 * Returns the {@link BlockFace} the digging action is performed on or null if no digging is in progress
	 * @return face
	 */
	BlockFace getBlockFace();

	/**
	 * Returns whether or not a digging action is currently in progress
	 * @return true if digging
	 */
	boolean isDigging();
	
	/**
	 * Returns the {@link WorldLocation} of the {@link Block} where the digging is performed
	 * @return location
	 * @throws NullPointerException if no digging is in progress
	 */
	WorldLocation getLocation();
	
	/**
	 * Returns the {@link Block} where the digging is performed or null of no digging is performed
	 * @return block
	 */
	Block getBlock();
	
	/**
	 * Starts a digging process for the specified position
	 * @param face
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	void startDigging(BlockFace face, World world, int x, int y, int z);
	
	/**
	 * Cancel the current digging process
	 */
	void cancelDigging();
	
	/**
	 * Manually finishes the digging process with verification
	 * @return true if digging was successfully performed
	 * @see #finishDigging(boolean)
	 */
	default boolean finishDigging() {
		return finishDigging(true);
	}
	
	/**
	 * Manually finishes the digging process.
	 * @param verify true if the success should be verified
	 * @return true if digging was successfully performed
	 */
	boolean finishDigging(boolean verify);
	
	/**
	 * Returns whether or not a digging process should finish when reaching 100%
	 * @return true if auto finish
	 */
	boolean isAutoFinish();
	
	/**
	 * Sets whether or not a a digging process should finish when reaching 100%
	 * @param autofinish true if auto finish
	 */
	void setAutoFinish(boolean autofinish);
	
	/**
	 * Returns whether or not the current digging process is finished.
	 * Will always return false if {@link #isDigging()} returns false
	 * @return true if finished
	 */
	boolean isFinished();

	/**
	 * Returns the current digging stage as value 0-9.
	 * Always returns 0 if no digging is in progress
	 * @return stage
	 */
	int getStage();

	/**
	 * Returns the x coordinate of the {@link Block} where the digging is performed
	 * @return x coordinate
	 * @throws NullPointerException if no digging is in progress
	 */
	int getX();

	/**
	 * Returns the y coordinate of the {@link Block} where the digging is performed
	 * @return y coordinate
	 * @throws NullPointerException if no digging is in progress
	 */
	int getY();

	/**
	 * Returns the z coordinate of the {@link Block} where the digging is performed
	 * @return z coordinate
	 * @throws NullPointerException if no digging is in progress
	 */
	int getZ();
	
	/**
	 * Sends the current digging animation state to all viewers of this handler
	 */
	void sendAnimation();
	
	/**
	 * Sends the current digging animation state to the given player
	 * @param player
	 */
	void sendAnimation(Player player);

}
