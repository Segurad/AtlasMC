package de.atlasmc.world;

import java.util.Collection;

import de.atlasmc.entity.Entity;
import de.atlasmc.util.annotation.NotNull;

public interface EntityTracker {
	
	@NotNull
	World getWorld();
	
	@NotNull
	Collection<Entity> getEntities();
	
	@NotNull
	<T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz);
	
	Entity getEntity(int entityID);

	Collection<Entity> getEntities(int x, int z);
	
	<C extends Collection<Entity>> C getEntities(int x, int z, C entities);

	<T extends Entity> Collection<T> getEntitesByClasses(int x, int z, Class<T> clazz);

	<T extends Entity, C extends Collection<T>> C getEntitesByClasses(int x, int z, Class<T> clazz, C entities);

	/**
	 * Tracks the given entity. should be called by the entity itself on {@link Entity#spawn(World, double, double, double, float, float)}
	 * Returns the tracker binding the entity should use to communicate with the tracker and that contains the entities id
	 * @param coreEntity
	 * @param x
	 * @param y
	 * @param z
	 * @param tracker
	 * @return
	 */
	TrackerBinding register(Entity entity, double x, double y, double z, Perception<?> tracker);
	
	/**
	 * Used by the to update nearby entities to the given entity
	 * @param <E>
	 */
	public static interface Perception<E extends Entity> {

		Class<E> tracking();
		
		void add(E entity);
		
		void remove(E entity);
		
	}
	
	/**
	 * Class for updating informations form the entity to the tracker
	 */
	public static interface TrackerBinding {
		
		void updatePosition(double x, double y, double z);
		
		void updatePerception(Perception<?> perception);
		
		void updatePerceptionDistance(double distance);
		
		void unregister();

		int getID();
		
	}
	
}
