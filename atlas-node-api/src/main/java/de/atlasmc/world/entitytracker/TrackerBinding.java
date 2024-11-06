package de.atlasmc.world.entitytracker;

/**
 * Class for updating informations form the entity to the tracker
 */
public interface TrackerBinding {
	
	/**
	 * Updates the position of the entity
	 * @param x
	 * @param y
	 * @param z
	 */
	void updatePosition(double x, double y, double z);
	
	/**
	 * Sets the perception of this entity
	 * @param perception
	 */
	void updatePerception(EntityPerception perception);
	
	/**
	 * Updates the ticking state of this entity
	 * @param ticking
	 */
	void updateTicking(boolean ticking);
	
	/**
	 * Notifies the tracker that the perceptions distance has changed
	 */
	void updatePerceptionDistance();
	
	/**
	 * Unregisters this binding and so the entity from the tracker and makes it invalid
	 */
	void unregister();

	/**
	 * Returns the entities id within the tracker
	 * @return entity id
	 */
	int getID();
	
}