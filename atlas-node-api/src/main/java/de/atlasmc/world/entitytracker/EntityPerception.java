package de.atlasmc.world.entitytracker;

import de.atlasmc.entity.Entity;
import de.atlasmc.util.annotation.NotNull;

/**
 * Used by the to update nearby entities
 * @param <E>
 */
public interface EntityPerception {

	/**
	 * Returns the class of which entities a perceived
	 * @return
	 */
	@NotNull
	Class<? extends Entity> tracking();
	
	/**
	 * Called by the tracker if a entity that is a instance of {@link #tracking()} is in {@link #range()}
		 * @param entity
	 */
	void add(Entity entity);
	
	/**
	 * Called by the tracker if a entity was removed or leaves the {@link #range()}
	 * @param entity
	 */
	void remove(Entity entity);
	
	/**
	 * Returns the perception range of the entity
	 * @return distance
	 */
	double range();
	
}
