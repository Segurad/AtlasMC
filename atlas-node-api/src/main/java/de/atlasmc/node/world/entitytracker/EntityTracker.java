package de.atlasmc.node.world.entitytracker;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.world.World;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface EntityTracker extends Tickable {
	
	@Nullable
	<T extends Entity> TrackingTarget<T> getTarget(Class<T> clazz);
	
	@NotNull
	<T extends Entity> TrackingTarget<T> createTarget(Class<T> clazz);
	
	@NotNull
	Collection<TrackingTarget<? extends Entity>> getTargets();
	
	@NotNull
	Collection<Entity> getEntities();
	
	@NotNull
	<T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz);
	
	@Nullable
	Entity getEntity(int entityID);
	
	@Nullable
	Entity getEntity(UUID uuid);

	@NotNull
	Collection<Entity> getEntities(int x, int z);
	
	<C extends Collection<Entity>> C getEntities(int x, int z, C entities);

	<T extends Entity> Collection<T> getEntitesByClasses(int x, int z, Class<T> clazz);

	<T extends Entity, C extends Collection<T>> C getEntitesByClasses(int x, int z, Class<T> clazz, C entities);
	
	/**
	 * Tracks the given entity. should be called by the entity itself on {@link Entity#spawn(World, double, double, double, float, float)}
	 * Returns the tracker binding the entity should use to communicate with the tracker and that contains the entities id
	 * The perception will be automatically located relocated using the head position of the given entity.
	 * @param entity the entity to track
	 * @param perception the perception of the entity
	 * @return binding
	 */
	@NotNull
	TrackerBinding register(Entity entity, EntityPerception perception);
	
	boolean isRegistered(Entity entity);
	
}
